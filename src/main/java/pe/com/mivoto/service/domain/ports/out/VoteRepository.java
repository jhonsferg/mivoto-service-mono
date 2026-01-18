package pe.com.mivoto.service.domain.ports.out;

import pe.com.mivoto.service.domain.enums.VoteStatus;
import pe.com.mivoto.service.domain.model.Vote;
import pe.com.mivoto.service.domain.model.VoteRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Output port for vote data access.
 * Manages persistence and retrieval of votes and their integrity records.
 */
public interface VoteRepository {

    /**
     * Persists a newly cast vote.
     *
     * @param vote The vote to save.
     * @return The saved Vote.
     */
    Vote save(Vote vote);

    /**
     * Updates an existing vote (e.g., status change).
     *
     * @param vote The vote to update.
     * @return The updated Vote.
     */
    Vote update(Vote vote);

    /**
     * Finds a vote by its ID.
     *
     * @param id The vote ID.
     * @return An Optional containing the Vote if found.
     */
    Optional<Vote> findById(Long id);

    /**
     * Finds a vote by its unique hash.
     *
     * @param voteHash The vote hash.
     * @return An Optional containing the Vote if found.
     */
    Optional<Vote> findByVoteHash(String voteHash);

    /**
     * Retrieves all votes cast by a specific user.
     *
     * @param userId The user ID.
     * @return A list of votes.
     */
    List<Vote> findByUserId(Long userId);

    /**
     * Retrieves all votes cast in a specific election.
     *
     * @param electionId The election ID.
     * @return A list of votes.
     */
    List<Vote> findByElectionId(Long electionId);

    /**
     * Retrieves all votes cast for a specific candidate.
     *
     * @param candidateId The candidate ID.
     * @return A list of votes.
     */
    List<Vote> findByCandidateId(Long candidateId);

    /**
     * Checks if a user has already voted in a specific election.
     *
     * @param userId     The user ID.
     * @param electionId The election ID.
     * @return true if a vote exists, false otherwise.
     */
    boolean existsByUserIdAndElectionId(Long userId, Long electionId);

    /**
     * Finds the specific vote cast by a user in an election.
     *
     * @param userId     The user ID.
     * @param electionId The election ID.
     * @return An Optional containing the Vote if found.
     */
    Optional<Vote> findByUserIdAndElectionId(Long userId, Long electionId);

    /**
     * Counts the total votes in an election.
     *
     * @param electionId The election ID.
     * @return The vote count.
     */
    Long countByElectionId(Long electionId);

    /**
     * Counts the total votes for a candidate.
     *
     * @param candidateId The candidate ID.
     * @return The vote count.
     */
    Long countByCandidateId(Long candidateId);

    /**
     * Retrieves all votes in the system.
     *
     * @return A list of all votes.
     */
    List<Vote> findAll();

    /**
     * Finds votes with a specific status (e.g., PENDING, CONFIRMED).
     *
     * @param status The vote status.
     * @return A list of matching votes.
     */
    List<Vote> findByStatus(VoteStatus status);

    /**
     * Finds votes cast within a specific time range.
     *
     * @param startDate The start date.
     * @param endDate   The end date.
     * @return A list of votes.
     */
    List<Vote> findByVotedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Deletes a vote by its ID.
     *
     * @param id The vote ID.
     */
    void deleteById(Long id);

    /**
     * Persists a vote verification record (immutable record).
     *
     * @param voteRecord The record to save.
     * @return The saved VoteRecord.
     */
    VoteRecord saveVoteRecord(VoteRecord voteRecord);

    /**
     * Finds a vote record by its hash.
     *
     * @param voteHash The vote hash.
     * @return An Optional containing the VoteRecord if found.
     */
    Optional<VoteRecord> findVoteRecordByHash(String voteHash);

    /**
     * Finds vote records associated with a user.
     *
     * @param userId The user ID.
     * @return A list of vote records.
     */
    List<VoteRecord> findVoteRecordsByUserId(Long userId);

    /**
     * Finds vote records for an election.
     *
     * @param electionId The election ID.
     * @return A list of vote records.
     */
    List<VoteRecord> findVoteRecordsByElectionId(Long electionId);

    /**
     * Retrieves all vote verification records.
     *
     * @return A list of all records.
     */
    List<VoteRecord> findAllVoteRecords();
}
