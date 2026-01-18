package pe.com.mivoto.service.application.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public Election getElectionById(Long electionId) {
        return this.electionRepository.findById(electionId)
                .orElseThrow(() -> new InvalidElectionException("Elección no encontrada"));
    }

    public List<Election> getActiveElections() {
        return this.electionRepository.findActiveElections();
    }

    public List<Election> getElectionsByStatus(ElectionStatus status) {
        return this.electionRepository.findByStatus(status);
    }

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
        Double participationRate = 0.0; // TODO: Calculate based on eligible voters count if available

        return new pe.com.mivoto.service.domain.ports.in.ElectionUseCase.ElectionStatistics(totalVotes, totalVotes, 0L,
                participationRate, leadingCandidate);
    }

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
