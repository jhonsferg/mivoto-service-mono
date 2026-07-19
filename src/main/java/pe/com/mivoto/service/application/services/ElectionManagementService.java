package pe.com.mivoto.service.application.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;
import pe.com.mivoto.service.datastructures.implementations.ElectionGraph;
import pe.com.mivoto.service.domain.enums.ElectionStatus;
import pe.com.mivoto.service.domain.exceptions.InvalidElectionException;
import pe.com.mivoto.service.domain.model.Candidate;
import pe.com.mivoto.service.domain.model.Election;
import pe.com.mivoto.service.domain.ports.in.ElectionUseCase;
import pe.com.mivoto.service.domain.ports.out.CandidateRepository;
import pe.com.mivoto.service.domain.ports.out.ElectionRepository;
import pe.com.mivoto.service.domain.ports.out.VoteRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Application service for managing election lifecycles.
 * Implements {@link ElectionUseCase} to handle creation, updates, status
 * changes,
 * and result calculation for elections.
 * Integrates with {@link ElectionGraph} for hierarchical election management.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ElectionManagementService implements ElectionUseCase {
    private final ElectionRepository electionRepository;
    private final CandidateRepository candidateRepository;
    private final VoteRepository voteRepository;
    private final AuditService auditService;

    private final ElectionGraph electionGraph;

    /**
     * Creates a new election with DRAFT status.
     *
     * @param election  The election data.
     * @param createdBy The ID of the user creating the election.
     * @return The created election.
     */
    @Transactional
    public Election createElection(Election election, Long createdBy) {
        log.info("Creando nueva elección: {}", election.getTitle());

        validateElectionDates(election);
        election.setStatus(ElectionStatus.DRAFT);
        election.setCreatedAt(LocalDateTime.now());
        election.setCreatedBy(createdBy);

        Election savedElection = this.electionRepository.save(election);
        this.electionGraph.addElection(savedElection, ElectionGraph.ElectionLevel.NATIONAL);
        this.auditService.logElectionCreated(savedElection.getId(), createdBy);

        log.info("Elección creada con ID: {}", savedElection.getId());
        return savedElection;
    }

    /**
     * Updates an existing election's details.
     *
     * @param electionId The ID of the election to update.
     * @param election   The new election data.
     * @return The updated election.
     */
    @Transactional
    public Election updateElection(Long electionId, Election election) {
        log.info("Actualizando elección: {}", electionId);

        Election existing = this.electionRepository.findById(electionId)
                .orElseThrow(() -> new InvalidElectionException("Elección no encontrada"));

        if (existing.isClosed() || existing.getStatus() == ElectionStatus.CANCELLED) {
            throw new InvalidElectionException("No se puede actualizar una elección cerrada o cancelada");
        }

        existing.setTitle(election.getTitle());
        existing.setDescription(election.getDescription());
        existing.setStartDate(election.getStartDate());
        existing.setEndDate(election.getEndDate());
        existing.setUpdatedAt(LocalDateTime.now());

        Election updated = this.electionRepository.update(existing);

        this.auditService.logElectionUpdated(electionId);

        return updated;
    }

    /**
     * Retrieves an election by its ID.
     *
     * @param electionId The election ID.
     * @return The Election object.
     */
    public Election getElectionById(Long electionId) {
        return this.electionRepository.findById(electionId)
                .orElseThrow(() -> new InvalidElectionException("Elección no encontrada"));
    }

    /**
     * Retrieves all elections.
     *
     * @return List of all elections.
     */
    @Transactional(readOnly = true)
    public List<Election> getAllElections() {
        return this.electionRepository.findAll();
    }

    /**
     * Retrieves all elections that are currently active.
     *
     * @return List of active elections.
     */
    public List<Election> getActiveElections() {
        return this.electionRepository.findActiveElections();
    }

    /**
     * Retrieves elections filtered by their status.
     *
     * @param status The election status.
     * @return List of elections with the specified status.
     */
    public List<Election> getElectionsByStatus(ElectionStatus status) {
        return this.electionRepository.findByStatus(status);
    }

    public void deleteElectionById(String id) {
        try {
            long electionId = Long.parseLong(id);
            Optional<Election> election = this.electionRepository.findById(electionId);
            if (election.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found election with id " + id);
            this.electionRepository.deleteById(electionId);
        } catch (NumberFormatException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id format");
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error has occurred");
        }
    }

    /**
     * Schedules a draft election, transitioning its status to SCHEDULED.
     *
     * @param electionId The ID of the election to schedule.
     */
    @Transactional
    public void scheduleElection(Long electionId) {
        Election election = this.getElectionById(electionId);

        if (election.getStatus() != ElectionStatus.DRAFT) {
            throw new InvalidElectionException("Solo se pueden programar elecciones en estado DRAFT");
        }

        election.schedule();
        this.electionRepository.update(election);

        this.auditService.logElectionUpdated(electionId);

        log.info("Elección programada: {}", electionId);
    }

    /**
     * Starts a scheduled election.
     *
     * @param electionId The ID of the election to start.
     */
    @Transactional
    public void startElection(Long electionId) {
        Election election = this.getElectionById(electionId);

        if (election.getStatus() != ElectionStatus.SCHEDULED) {
            throw new InvalidElectionException("Solo se pueden iniciar elecciones programadas");
        }

        if (election.getCandidates().isEmpty()) {
            throw new InvalidElectionException("La elección debe tener al menos un candidato");
        }

        election.start();
        this.electionRepository.update(election);

        this.auditService.logElectionStarted(electionId);

        log.info("Elección iniciada: {}", electionId);
    }

    /**
     * Closes an active election.
     *
     * @param electionId The ID of the election to close.
     */
    @Transactional
    public void closeElection(Long electionId) {
        Election election = this.getElectionById(electionId);

        if (election.getStatus() != ElectionStatus.ACTIVE) {
            throw new InvalidElectionException("Solo se pueden cerrar elecciones activas");
        }

        election.close();
        this.electionRepository.update(election);

        this.auditService.logElectionClosed(electionId);

        log.info("Elección cerrada: {}", electionId);
    }

    /**
     * Changes the status of an election to CANCELLED.
     *
     * @param electionId The ID of the election to cancel.
     * @param reason     The justification for cancellation.
     * @throws InvalidElectionException if the election is already closed.
     */
    @Transactional
    public void cancelElection(Long electionId, String reason) {
        Election election = this.getElectionById(electionId);

        if (election.isClosed()) {
            throw new InvalidElectionException("No se puede cancelar una elección cerrada");
        }

        election.cancel();
        this.electionRepository.update(election);

        this.auditService.logElectionCancelled(electionId, reason);
    }

    /**
     * Calculates current results for an election.
     * Returns candidate-vote count pairs.
     *
     * @param electionId The election ID.
     * @return Map of Candidate to vote count.
     * @throws InvalidElectionException if election is DRAFT or SCHEDULED.
     */
    public Map<Candidate, Long> getElectionResults(Long electionId) {
        log.info("Obteniendo resultados de elección: {}", electionId);

        Election election = getElectionById(electionId);

        if (!election.isClosed() && election.getStatus() != ElectionStatus.ACTIVE) {
            throw new InvalidElectionException("La elección debe estar activa o cerrada para ver resultados");
        }

        List<Candidate> candidates = this.candidateRepository.findByElectionId(electionId);
        Map<Candidate, Long> results = new HashMap<>();

        for (Candidate candidate : candidates) {
            Long voteCount = this.voteRepository.countByCandidateId(candidate.getId());
            results.put(candidate, voteCount);
        }

        return results;
    }

    /**
     * Generates statistical abstract for an election.
     * Includes participation rates and leading candidate.
     *
     * @param electionId The election ID.
     * @return ElectionStatistics object.
     */
    public ElectionStatistics getElectionStatistics(Long electionId) {
        this.getElectionById(electionId);

        Long totalVotes = this.voteRepository.countByElectionId(electionId);
        List<Candidate> candidates = this.candidateRepository.findByElectionIdOrderByVoteCountDesc(electionId);
        Candidate leadingCandidate = candidates.isEmpty() ? null : candidates.get(0);
        // participationRate requires a total eligible-voters count which is not yet tracked in the system.
        // Return null so the client can display "N/A" instead of a misleading 0%.

        return new pe.com.mivoto.service.domain.ports.in.ElectionUseCase.ElectionStatistics(totalVotes, totalVotes, 0L,
                null, candidates.size(), leadingCandidate);
    }

    /**
     * Finds elections scheduled within a specific date range.
     *
     * @param startDate The start date of the range.
     * @param endDate   The end date of the range.
     * @return List of elections occurring within the range.
     */
    @Override
    public List<Election> findElectionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return this.electionRepository.findByDateRange(startDate, endDate);
    }

    /**
     * Establishes a hierarchical relationship between two elections in the graph.
     *
     * @param parentElectionId The parent election ID.
     * @param childElectionId  The child election ID.
     * @param type             The type of relationship (e.g., GEOGRAPHICAL,
     *                         ORGANIZATIONAL).
     */
    public void setElectionHierarchy(Long parentElectionId, Long childElectionId, ElectionGraph.RelationshipType type) {
        log.info("Estableciendo jerarquía: {} -> {}", parentElectionId, childElectionId);
        this.electionGraph.addRelationship(parentElectionId, childElectionId, type);
    }

    /**
     * Retrieves all direct sub-elections (children) of a given election.
     *
     * @param electionId The parent election ID.
     * @return List of children elections.
     */
    public List<Election> getSubElections(Long electionId) {
        return this.electionGraph.getSubElections(electionId);
    }

    /**
     * Retrieves the complete hierarchy (parents and children) for an election.
     *
     * @param electionId The election ID.
     * @return The ElectionHierarchy structure.
     */
    public ElectionGraph.ElectionHierarchy getElectionHierarchy(Long electionId) {
        return this.electionGraph.getHierarchy(electionId);
    }

    /**
     * Validates that election dates are logical (start in future, end after start).
     *
     * @param election The election to validate.
     */
    private void validateElectionDates(Election election) {
        LocalDateTime now = LocalDateTime.now();

        if (election.getStartDate().isBefore(now)) {
            throw new InvalidElectionException("La fecha de inicio debe ser futura");
        }

        if (election.getEndDate().isBefore(election.getStartDate())) {
            throw new InvalidElectionException("La fecha de fin debe ser posterior a la de inicio");
        }
    }

}
