package pe.com.mivoto.service.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.enums.VoteStatus;
import pe.com.mivoto.service.domain.model.Vote;
import pe.com.mivoto.service.domain.model.VoteRecord;
import pe.com.mivoto.service.domain.ports.out.VoteRepository;
import pe.com.mivoto.service.infrastructure.persistence.entities.VoteEntity;
import pe.com.mivoto.service.infrastructure.persistence.entities.VoteRecordEntity;
import pe.com.mivoto.service.infrastructure.persistence.mappers.VoteEntityMapper;
import pe.com.mivoto.service.infrastructure.persistence.mappers.VoteRecordEntityMapper;
import pe.com.mivoto.service.infrastructure.persistence.repositories.JpaVoteRecordRepository;
import pe.com.mivoto.service.infrastructure.persistence.repositories.JpaVoteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the Vote Output Port (VoteRepository) using the JPA
 * repository.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VoteRepositoryAdapter implements VoteRepository {

    private final JpaVoteRepository jpaVoteRepository;
    private final JpaVoteRecordRepository jpaVoteRecordRepository;
    private final VoteEntityMapper voteEntityMapper;
    private final VoteRecordEntityMapper voteRecordEntityMapper;

    /**
     * Saves a new vote.
     *
     * @param vote The vote to save.
     * @return The saved vote.
     */
    @Override
    public Vote save(Vote vote) {
        log.debug("Guardando voto: {}", vote.getVoteHash());
        VoteEntity entity = voteEntityMapper.toEntity(vote);
        VoteEntity saved = jpaVoteRepository.save(entity);
        return voteEntityMapper.toDomain(saved);
    }

    /**
     * Updates an existing vote.
     *
     * @param vote The vote updates.
     * @return The updated vote.
     */
    @Override
    public Vote update(Vote vote) {
        log.debug("Actualizando voto: {}", vote.getId());
        VoteEntity entity = voteEntityMapper.toEntity(vote);
        VoteEntity updated = jpaVoteRepository.save(entity);
        return voteEntityMapper.toDomain(updated);
    }

    /**
     * Finds a vote by ID.
     *
     * @param id The vote ID.
     * @return Optional containing the vote if found.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Optional<Vote> findById(Long id) {
        return jpaVoteRepository.findById(id)
                .map(voteEntityMapper::toDomain);
    }

    /**
     * Finds a vote by its unique hash.
     *
     * @param voteHash The vote hash.
     * @return Optional containing the vote if found.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Optional<Vote> findByVoteHash(String voteHash) {
        return jpaVoteRepository.findByVoteHash(voteHash)
                .map(voteEntityMapper::toDomain);
    }

    /**
     * Finds all votes cast by a specific user.
     *
     * @param userId The user ID.
     * @return List of user's votes.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Vote> findByUserId(Long userId) {
        return jpaVoteRepository.findByUserId(userId).stream()
                .map(voteEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds all votes for a specific election.
     *
     * @param electionId The election ID.
     * @return List of votes.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Vote> findByElectionId(Long electionId) {
        return jpaVoteRepository.findByElectionId(electionId).stream()
                .map(voteEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds all votes for a specific candidate.
     *
     * @param candidateId The candidate ID.
     * @return List of votes.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Vote> findByCandidateId(Long candidateId) {
        return jpaVoteRepository.findByCandidateId(candidateId).stream()
                .map(voteEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Checks if a user has already voted in an election.
     *
     * @param userId     The user ID.
     * @param electionId The election ID.
     * @return true if the user has voted.
     */
    @Override
    public boolean existsByUserIdAndElectionId(Long userId, Long electionId) {
        return jpaVoteRepository.existsByUserIdAndElectionId(userId, electionId);
    }

    /**
     * Finds a specific vote by user and election.
     *
     * @param userId     The user ID.
     * @param electionId The election ID.
     * @return Optional containing the vote if found.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Optional<Vote> findByUserIdAndElectionId(Long userId, Long electionId) {
        return jpaVoteRepository.findByUserIdAndElectionId(userId, electionId)
                .map(voteEntityMapper::toDomain);
    }

    /**
     * Counts the total votes for an election.
     *
     * @param electionId The election ID.
     * @return The count of votes.
     */
    @Override
    public Long countByElectionId(Long electionId) {
        return jpaVoteRepository.countByElectionId(electionId);
    }

    /**
     * Counts the votes for a specific candidate.
     *
     * @param candidateId The candidate ID.
     * @return The count of votes.
     */
    @Override
    public Long countByCandidateId(Long candidateId) {
        return jpaVoteRepository.countByCandidateId(candidateId);
    }

    /**
     * Retrieves all votes.
     *
     * @return List of all votes.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Vote> findAll() {
        return jpaVoteRepository.findAll().stream()
                .map(voteEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds votes by their status.
     *
     * @param status The vote status.
     * @return List of matching votes.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Vote> findByStatus(VoteStatus status) {
        return jpaVoteRepository.findByStatus(status).stream()
                .map(voteEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds votes cast within a date range.
     *
     * @param startDate The start date.
     * @param endDate   The end date.
     * @return List of matching votes.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Vote> findByVotedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return jpaVoteRepository.findByVotedAtBetween(startDate, endDate).stream()
                .map(voteEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a vote by ID.
     *
     * @param id The vote ID.
     */
    @Override
    public void deleteById(Long id) {
        jpaVoteRepository.deleteById(id);
    }

    /**
     * Saves a vote record (immutable history).
     *
     * @param voteRecord The vote record to save.
     * @return The saved vote record.
     */
    @Override
    public VoteRecord saveVoteRecord(VoteRecord voteRecord) {
        log.debug("Guardando registro de voto: {}", voteRecord.getVoteHash());
        VoteRecordEntity entity = voteRecordEntityMapper.toEntity(voteRecord);
        VoteRecordEntity saved = jpaVoteRecordRepository.save(entity);
        return voteRecordEntityMapper.toDomain(saved);
    }

    /**
     * Finds a vote record by hash.
     *
     * @param voteHash The vote hash.
     * @return Optional containing the record if found.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Optional<VoteRecord> findVoteRecordByHash(String voteHash) {
        return jpaVoteRecordRepository.findByVoteHash(voteHash)
                .map(voteRecordEntityMapper::toDomain);
    }

    /**
     * Finds vote records for one user.
     *
     * @param userId The user ID.
     * @return List of vote records.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<VoteRecord> findVoteRecordsByUserId(Long userId) {
        return jpaVoteRecordRepository.findByUserId(userId).stream()
                .map(voteRecordEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds vote records for one election.
     *
     * @param electionId The election ID.
     * @return List of vote records.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<VoteRecord> findVoteRecordsByElectionId(Long electionId) {
        return jpaVoteRecordRepository.findByElectionId(electionId).stream()
                .map(voteRecordEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all vote records.
     *
     * @return List of all vote records.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<VoteRecord> findAllVoteRecords() {
        return jpaVoteRecordRepository.findAll().stream()
                .map(voteRecordEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Counts the total number of votes.
     *
     * @return The count.
     */
    @Override
    public Long count() {
        return jpaVoteRepository.count();
    }

    /**
     * Counts votes by election and groups by party.
     *
     * @param electionId The election ID.
     * @return Map of party name to vote count.
     */
    @Override
    public Map<String, Long> countByElectionIdGroupByParty(Long electionId) {
        return jpaVoteRepository.countByElectionIdGroupedByParty(electionId).stream()
                .collect(Collectors.toMap(
                        result -> (String) result[0],
                        result -> (Long) result[1]));
    }

    /**
     * Counts votes by election and groups by candidate ID.
     *
     * @param electionId The election ID.
     * @return Map of candidate ID to vote count.
     */
    @Override
    public Map<Long, Long> countByElectionIdGroupByCandidate(Long electionId) {
        return jpaVoteRepository.countByElectionIdGroupedByCandidate(electionId).stream()
                .collect(Collectors.toMap(
                        result -> (Long) result[0],
                        result -> (Long) result[1]));
    }
}
