package pe.com.mivoto.service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.mivoto.service.domain.enums.VoteStatus;
import pe.com.mivoto.service.infrastructure.persistence.entities.VoteEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for Votes.
 * Provides database access methods for managing VoteEntity.
 */
@Repository
public interface JpaVoteRepository extends JpaRepository<VoteEntity, Long> {

    /**
     * Finds a vote by its unique hash.
     *
     * @param voteHash The vote hash.
     * @return Optional containing the vote if found.
     */
    Optional<VoteEntity> findByVoteHash(String voteHash);

    /**
     * Finds votes by user ID.
     *
     * @param userId The user ID.
     * @return List of votes.
     */
    List<VoteEntity> findByUserId(Long userId);

    /**
     * Finds votes by election ID.
     *
     * @param electionId The election ID.
     * @return List of votes.
     */
    List<VoteEntity> findByElectionId(Long electionId);

    /**
     * Finds votes by candidate ID.
     *
     * @param candidateId The candidate ID.
     * @return List of votes.
     */
    List<VoteEntity> findByCandidateId(Long candidateId);

    /**
     * Checks if a user has voted in a specific election.
     *
     * @param userId     The user ID.
     * @param electionId The election ID.
     * @return true if vote exists, false otherwise.
     */
    boolean existsByUserIdAndElectionId(Long userId, Long electionId);

    /**
     * Finds a vote by user ID and election ID.
     *
     * @param userId     The user ID.
     * @param electionId The election ID.
     * @return Optional containing the vote if found.
     */
    Optional<VoteEntity> findByUserIdAndElectionId(Long userId, Long electionId);

    /**
     * Counts votes for a specific election.
     *
     * @param electionId The election ID.
     * @return The count of votes.
     */
    @Query("SELECT COUNT(v) FROM VoteEntity v WHERE v.electionId = :electionId")
    Long countByElectionId(Long electionId);

    /**
     * Counts votes for a specific candidate.
     *
     * @param candidateId The candidate ID.
     * @return The count of votes.
     */
    @Query("SELECT COUNT(v) FROM VoteEntity v WHERE v.candidateId = :candidateId")
    Long countByCandidateId(Long candidateId);

    /**
     * Finds votes by status.
     *
     * @param status The vote status.
     * @return List of votes.
     */
    List<VoteEntity> findByStatus(VoteStatus status);

    /**
     * Finds votes cast within a date range.
     *
     * @param startDate The start date.
     * @param endDate   The end date.
     * @return List of votes.
     */
    List<VoteEntity> findByVotedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Gets vote counts grouped by candidate party for an election.
     * Optimized query to avoid N+1 problem.
     *
     * @param electionId The election ID.
     * @return Map of party name to vote count.
     */
    @Query("SELECT c.party, COUNT(v) " +
            "FROM VoteEntity v " +
            "JOIN CandidateEntity c ON v.candidateId = c.id " +
            "WHERE v.electionId = :electionId " +
            "GROUP BY c.party")
    List<Object[]> countByElectionIdGroupedByParty(Long electionId);

    /**
     * Gets vote counts grouped by candidate for an election.
     * Optimized query to avoid N+1 problem.
     *
     * @param electionId The election ID.
     * @return Map of candidate ID to vote count.
     */
    @Query("SELECT v.candidateId, COUNT(v) " +
            "FROM VoteEntity v " +
            "WHERE v.electionId = :electionId " +
            "GROUP BY v.candidateId")
    List<Object[]> countByElectionIdGroupedByCandidate(Long electionId);
}
