package pe.com.mivoto.service.application.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.mivoto.service.datastructures.implementations.CandidateSearchTree;
import pe.com.mivoto.service.domain.exceptions.InvalidElectionException;
import pe.com.mivoto.service.domain.model.Candidate;
import pe.com.mivoto.service.domain.model.Election;
import pe.com.mivoto.service.domain.ports.out.CandidateRepository;
import pe.com.mivoto.service.domain.ports.out.ElectionRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Application service for managing Candidates.
 * Handles registration, updates, retrieval, and status changes for candidates
 * in elections.
 * Integrates with {@link CandidateSearchTree} for optimized searches.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CandidateService implements pe.com.mivoto.service.domain.ports.in.CandidateUseCase {
    private final CandidateRepository candidateRepository;
    private final ElectionRepository electionRepository;
    private final AuditService auditService;

    private final CandidateSearchTree candidateSearchTree;

    /**
     * Registers a new candidate in an election after validating the election state
     * and ensuring no number collision exists.
     *
     * @param candidate The candidate data to register.
     * @return The registered candidate with generated ID.
     * @throws InvalidElectionException if election is closed, cancelled, or number
     *                                  is taken.
     */
    @Transactional
    public Candidate registerCandidate(Candidate candidate) {
        log.info("Registrando candidato: {} para elección: {}", candidate.getName(), candidate.getElectionId());

        Election election = this.electionRepository.findById(candidate.getElectionId())
                .orElseThrow(() -> new InvalidElectionException("Elección no encontrada"));

        if (election.isClosed()
                || election.getStatus() == pe.com.mivoto.service.domain.enums.ElectionStatus.CANCELLED) {
            throw new InvalidElectionException("No se pueden agregar candidatos a una elección cerrada o cancelada");
        }

        if (this.candidateRepository.existsByElectionIdAndNumber(candidate.getElectionId(), candidate.getNumber())) {
            throw new InvalidElectionException("Ya existe un candidato con ese número");
        }

        candidate.setActive(true);
        candidate.setVoteCount(0);
        candidate.setCreatedAt(LocalDateTime.now());

        Candidate savedCandidate = this.candidateRepository.save(candidate);
        this.candidateSearchTree.insert(savedCandidate);
        this.candidateSearchTree.insert(savedCandidate);

        this.auditService.logCandidateAdded(savedCandidate.getId(), candidate.getElectionId());

        log.info("Candidato registrado con ID: {}", savedCandidate.getId());
        return savedCandidate;
    }

    /**
     * Updates an existing candidate's information and synchronizes the search tree.
     *
     * @param candidateId The ID of the candidate to update.
     * @param candidate   The new candidate data.
     * @return The updated candidate.
     */
    @Transactional
    public Candidate updateCandidate(Long candidateId, Candidate candidate) {
        log.info("Actualizando candidato: {}", candidateId);

        Candidate existing = this.candidateRepository.findById(candidateId)
                .orElseThrow(() -> new InvalidElectionException("Candidato no encontrado"));

        existing.setName(candidate.getName());
        existing.setParty(candidate.getParty());
        existing.setDescription(candidate.getDescription());
        existing.setPhotoUrl(candidate.getPhotoUrl());
        existing.setUpdatedAt(LocalDateTime.now());

        Candidate updated = this.candidateRepository.update(existing);
        this.candidateSearchTree.delete(existing.getNumber());
        this.candidateSearchTree.insert(updated);
        this.candidateSearchTree.insert(updated);

        this.auditService.logCandidateUpdated(candidateId);

        return updated;
    }

    /**
     * Retrieves a candidate by their unique ID.
     *
     * @param candidateId The candidate ID.
     * @return The Candidate object.
     */
    public Candidate getCandidateById(Long candidateId) {
        return this.candidateRepository.findById(candidateId)
                .orElseThrow(() -> new InvalidElectionException("Candidato no encontrado"));
    }

    /**
     * Retrieves all candidates registered for a specific election.
     *
     * @param electionId The election ID.
     * @return List of candidates.
     */
    public List<Candidate> getCandidatesByElection(Long electionId) {
        return this.candidateRepository.findByElectionId(electionId);
    }

    /**
     * Finds a candidate by their ballot number, checking the search tree first
     * for optimization.
     *
     * @param electionId The election ID.
     * @param number     The candidate's ballot number.
     * @return The matching candidate.
     */
    public Candidate findCandidateByNumber(Long electionId, Integer number) {
        log.debug("Buscando candidato por número: {} en elección: {}", number, electionId);

        Optional<Candidate> candidateOpt = this.candidateSearchTree.findByNumber(number);

        if (candidateOpt.isPresent()) {
            Candidate candidate = candidateOpt.get();
            if (candidate.getElectionId().equals(electionId)) {
                return candidate;
            }
        }

        return this.candidateRepository.findByElectionIdAndNumber(electionId, number)
                .orElseThrow(() -> new InvalidElectionException("Candidato no encontrado"));
    }

    /**
     * Enables a candidate to receive votes.
     *
     * @param candidateId The candidate ID.
     */
    @Transactional
    public void activateCandidate(Long candidateId) {
        log.info("Activando candidato: {}", candidateId);

        Candidate candidate = getCandidateById(candidateId);
        candidate.activate();
        this.candidateRepository.update(candidate);
        this.candidateRepository.update(candidate);

        this.auditService.logCandidateActivated(candidateId);
    }

    /**
     * Prevents a candidate from receiving further votes.
     *
     * @param candidateId The candidate ID.
     */
    @Transactional
    public void deactivateCandidate(Long candidateId) {
        log.info("Desactivando candidato: {}", candidateId);

        Candidate candidate = getCandidateById(candidateId);
        candidate.deactivate();
        this.candidateRepository.update(candidate);
        this.candidateRepository.update(candidate);

        this.auditService.logCandidateDeactivated(candidateId);
    }

    /**
     * Permanently removes a candidate if they have zero votes.
     *
     * @param candidateId The candidate ID.
     * @throws InvalidElectionException if the candidate has already received votes.
     */
    @Transactional
    public void deleteCandidate(Long candidateId) {
        log.warn("Eliminando candidato: {}", candidateId);

        Candidate candidate = getCandidateById(candidateId);
        Long voteCount = Long.valueOf(this.candidateRepository.findById(candidateId)
                .map(Candidate::getVoteCount)
                .orElse(0));

        if (voteCount > 0) {
            throw new InvalidElectionException("No se puede eliminar un candidato con votos");
        }

        this.candidateSearchTree.delete(candidate.getNumber());
        this.candidateRepository.deleteById(candidateId);
        this.candidateRepository.deleteById(candidateId);

        this.auditService.logCandidateDeleted(candidateId);
    }

    /**
     * Retrieves active candidates for an election.
     *
     * @param electionId The election ID.
     * @return List of active candidates.
     */
    public List<Candidate> getActiveCandidates(Long electionId) {
        return this.candidateRepository.findActiveByElectionId(electionId);
    }

    /**
     * Finds candidates by their political party affiliation.
     *
     * @param party The party name.
     * @return List of candidates.
     */
    public List<Candidate> findCandidatesByParty(String party) {
        return this.candidateRepository.findByParty(party);
    }

    /**
     * Retrieves all candidates for an election ordered by their ballot number
     * using the search tree.
     *
     * @param electionId The election ID.
     * @return Sorted list of candidates.
     */
    public List<Candidate> getCandidatesOrderedByNumber(Long electionId) {
        List<Candidate> orderedCandidates = this.candidateSearchTree.getAllCandidatesOrdered();

        return orderedCandidates.stream()
                .filter(c -> c.getElectionId().equals(electionId))
                .toList();
    }

    /**
     * Synchronizes all candidates of an election with the optimized search tree.
     *
     * @param electionId The election ID.
     */
    @Transactional(readOnly = true)
    public void syncCandidatesToTree(Long electionId) {
        log.info("Sincronizando candidatos de elección {} al árbol", electionId);

        List<Candidate> candidates = this.candidateRepository.findByElectionId(electionId);

        for (Candidate candidate : candidates) {
            if (!this.candidateSearchTree.existsByNumber(candidate.getNumber())) {
                this.candidateSearchTree.insert(candidate);
            }
        }

        log.info("Sincronizados {} candidatos", candidates.size());
    }
}
