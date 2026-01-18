package pe.com.mivoto.service.domain.ports.in;

import pe.com.mivoto.service.domain.model.Vote;
import pe.com.mivoto.service.domain.model.VoteRecord;

import java.util.List;

/**
 * Input port for voting operations.
 * Handles the casting, verification, and retrieval of votes.
 */
public interface VotingUseCase {

    /**
     * Casts a vote for a candidate in a specific election.
     *
     * @param userId      The ID of the user casting the vote.
     * @param electionId  The ID of the election.
     * @param candidateId The ID of the selected candidate.
     * @return The created Vote object.
     */
    Vote castVote(Long userId, Long electionId, Long candidateId);

    /**
     * Verifies the integrity of a vote using its hash.
     *
     * @param voteHash The hash string of the vote.
     * @return The verified VoteRecord.
     */
    VoteRecord verifyVote(String voteHash);

    /**
     * Checks if a user has already voted in a specific election.
     *
     * @param userId     The user ID.
     * @param electionId The election ID.
     * @return true if the user has already voted, false otherwise.
     */
    boolean hasVoted(Long userId, Long electionId);

    /**
     * Retrieves the voting history for a particular user.
     *
     * @param userId The user ID.
     * @return A list of VoteRecords representing the user's history.
     */
    List<VoteRecord> getVotingHistory(Long userId);

    /**
     * Retrieves all votes cast in a specific election.
     *
     * @param electionId The election ID.
     * @return A list of all votes.
     */
    List<Vote> getVotesByElection(Long electionId);

    /**
     * Counts the total verified votes for a specific candidate.
     *
     * @param candidateId The candidate ID.
     * @return The total vote count.
     */
    Long countVotesByCandidate(Long candidateId);

    /**
     * Invalidates a previously cast vote.
     *
     * @param voteId The ID of the vote to invalidate.
     * @param reason The reason for invalidation.
     */
    void invalidateVote(Long voteId, String reason);
}
