package pe.com.mivoto.service.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.Candidate;
import pe.com.mivoto.service.domain.ports.out.CandidateRepository;
import pe.com.mivoto.service.infrastructure.persistence.entities.CandidateEntity;
import pe.com.mivoto.service.infrastructure.persistence.mappers.CandidateEntityMapper;
import pe.com.mivoto.service.infrastructure.persistence.repositories.JpaCandidateRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the Candidate Output Port (CandidateRepository) using the
 * JPA repository.
 * Handles the mapping between the Candidate domain model and the
 * CandidateEntity.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CandidateRepositoryAdapter implements CandidateRepository {

    private final JpaCandidateRepository jpaCandidateRepository;
    private final CandidateEntityMapper candidateEntityMapper;

    /**
     * Saves a new candidate.
     *
     * @param candidate The candidate to save.
     * @return The saved candidate.
     */
    @Override
    public Candidate save(Candidate candidate) {
        log.debug("Guardando candidato: {}", candidate.getName());
        CandidateEntity entity = candidateEntityMapper.toEntity(candidate);
        CandidateEntity saved = jpaCandidateRepository.save(entity);
        return candidateEntityMapper.toDomain(saved);
    }

    /**
     * Updates an existing candidate.
     *
     * @param candidate The candidate updates.
     * @return The updated candidate.
     */
    @Override
    public Candidate update(Candidate candidate) {
        log.debug("Actualizando candidato: {}", candidate.getId());
        CandidateEntity entity = candidateEntityMapper.toEntity(candidate);
        CandidateEntity updated = jpaCandidateRepository.save(entity);
        return candidateEntityMapper.toDomain(updated);
    }

    /**
     * Finds a candidate by ID.
     *
     * @param id The candidate ID.
     * @return Optional containing the candidate if found.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Optional<Candidate> findById(Long id) {
        return jpaCandidateRepository.findById(id)
                .map(candidateEntityMapper::toDomain);
    }

    /**
     * Retrieves all candidates.
     *
     * @return List of all candidates.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Candidate> findAll() {
        return jpaCandidateRepository.findAll().stream()
                .map(candidateEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds candidates by election ID.
     *
     * @param electionId The election ID.
     * @return List of candidates.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Candidate> findByElectionId(Long electionId) {
        return jpaCandidateRepository.findByElectionId(electionId).stream()
                .map(candidateEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds a candidate by election ID and ballot number.
     *
     * @param electionId The election ID.
     * @param number     The ballot number.
     * @return Optional containing the candidate if found.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Optional<Candidate> findByElectionIdAndNumber(Long electionId, Integer number) {
        return jpaCandidateRepository.findByElectionIdAndNumber(electionId, number)
                .map(candidateEntityMapper::toDomain);
    }

    /**
     * Finds active candidates for an election.
     *
     * @param electionId The election ID.
     * @return List of active candidates.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Candidate> findActiveByElectionId(Long electionId) {
        return jpaCandidateRepository.findActiveByElectionId(electionId).stream()
                .map(candidateEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds candidates by party affiliation.
     *
     * @param party The party name.
     * @return List of candidates.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Candidate> findByParty(String party) {
        return jpaCandidateRepository.findByParty(party).stream()
                .map(candidateEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds candidates with names containing the search string.
     *
     * @param name The name substring.
     * @return List of matching candidates.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Candidate> findByNameContaining(String name) {
        return jpaCandidateRepository.findByNameContainingIgnoreCase(name).stream()
                .map(candidateEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a candidate by ID.
     *
     * @param id The candidate ID.
     */
    @Override
    public void deleteById(Long id) {
        jpaCandidateRepository.deleteById(id);
    }

    /**
     * Checks if a candidate number already exists in an election.
     *
     * @param electionId The election ID.
     * @param number     The ballot number.
     * @return true if exists.
     */
    @Override
    public boolean existsByElectionIdAndNumber(Long electionId, Integer number) {
        return jpaCandidateRepository.existsByElectionIdAndNumber(electionId, number);
    }

    /**
     * Counts candidates for an election.
     *
     * @param electionId The election ID.
     * @return The count of candidates.
     */
    @Override
    public Long countByElectionId(Long electionId) {
        return jpaCandidateRepository.countByElectionId(electionId);
    }

    /**
     * Retrieves candidates for an election ordered by vote count.
     *
     * @param electionId The election ID.
     * @return List of candidates sorted by votes (descending).
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Candidate> findByElectionIdOrderByVoteCountDesc(Long electionId) {
        return jpaCandidateRepository.findByElectionIdOrderByVoteCountDesc(electionId).stream()
                .map(candidateEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}
