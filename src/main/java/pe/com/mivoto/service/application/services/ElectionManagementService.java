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
import pe.com.mivoto.service.domain.ports.out.CandidateRepository;
import pe.com.mivoto.service.domain.ports.out.ElectionRepository;
import pe.com.mivoto.service.domain.ports.out.VoteRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElectionManagementService {
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

        Election existing = this.electionRepository.findById(electionId).orElseThrow(() -> new InvalidElectionException("Elección no encontrada"));

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
        return this.electionRepository.findById(electionId).orElseThrow(() -> new InvalidElectionException("Elección no encontrada"));
    }

    public List<Election> getActiveElections() {
        return this.electionRepository.findActiveElections();
    }

    public List<Election> getElectionsByStatus(ElectionStatus status) {
        return this.electionRepository.findByStatus(status);
    }

    @Transactional
    public void startElection(Long electionId) {
        log.info("Iniciando elección: {}", electionId);

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
        log.info("Cerrando elección: {}", electionId);

        Election election = this.getElectionById(electionId);

        if (election.getStatus() != ElectionStatus.ACTIVE) {
            throw new InvalidElectionException("Solo se pueden cerrar elecciones activas");
        }

        election.close();
        this.electionRepository.update(election);
        this.auditService.logElectionClosed(electionId);

        log.info("Elección cerrada: {}", electionId);
    }

    @Transactional
    public void cancelElection(Long electionId, String reason) {
        log.warn("Cancelando elección: {} - Razón: {}", electionId, reason);

        Election election = this.getElectionById(electionId);

        if (election.isClosed()) {
            throw new InvalidElectionException("No se puede cancelar una elección cerrada");
        }

        election.cancel();
        this.electionRepository.update(election);
        this.auditService.logElectionCancelled(electionId, reason);
    }

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

    public ElectionStatistics getElectionStatistics(Long electionId) {
        Election election = this.getElectionById(electionId);

        Long totalVotes = this.voteRepository.countByElectionId(electionId);
        List<Candidate> candidates = this.candidateRepository.findByElectionIdOrderByVoteCountDesc(electionId);
        Candidate leadingCandidate = candidates.isEmpty() ? null : candidates.get(0);
        Double participationRate = 0.0;

        return new ElectionStatistics(totalVotes, totalVotes, 0L, participationRate, leadingCandidate);
    }

    public void setElectionHierarchy(Long parentElectionId, Long childElectionId, ElectionGraph.RelationshipType type) {
        log.info("Estableciendo jerarquía: {} -> {}", parentElectionId, childElectionId);
        this.electionGraph.addRelationship(parentElectionId, childElectionId, type);
    }

    public List<Election> getSubElections(Long electionId) {
        return this.electionGraph.getSubElections(electionId);
    }

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

    public record ElectionStatistics(
            Long totalVotes,
            Long totalValidVotes,
            Long totalInvalidVotes,
            Double participationRate,
            Candidate leadingCandidate
    ) {
    }
}
