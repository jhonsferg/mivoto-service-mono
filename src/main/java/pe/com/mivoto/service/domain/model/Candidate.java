package pe.com.mivoto.service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents a candidate running in an election.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {

    /**
     * Unique identifier for the candidate.
     */
    private Long id;

    /**
     * ID of the election this candidate is participating in.
     */
    private Long electionId;

    /**
     * The candidate's number on the electoral ballot.
     */
    private Integer number;

    /**
     * Full name of the candidate.
     */
    private String name;

    /**
     * Political party the candidate belongs to.
     */
    private String party;

    /**
     * Brief biography or description of the candidate's platform.
     */
    private String description;

    /**
     * URL to the candidate's official photograph.
     */
    private String photoUrl;

    /**
     * Indicates if the candidate is currently active in the election.
     */
    private Boolean active;

    /**
     * Current count of votes received by this candidate.
     */
    private Integer voteCount;

    /**
     * Timestamp when the candidate record was created.
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp when the candidate record was last updated.
     */
    private LocalDateTime updatedAt;

    /**
     * Checks if the candidate is active and eligible to receive votes.
     *
     * @return true if active, false otherwise.
     */
    public boolean canReceiveVotes() {
        return active != null && active;
    }

    /**
     * Increments the candidate's vote count by one.
     * Updates the last modification timestamp.
     */
    public void incrementVoteCount() {
        if (this.voteCount == null) {
            this.voteCount = 0;
        }
        this.voteCount++;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Activates the candidate, allowing them to participate in the election.
     */
    public void activate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Deactivates the candidate, preventing them from receiving further votes.
     */
    public void deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Retrieves the current total vote count for the candidate.
     *
     * @return The vote count, defaults to 0 if null.
     */
    public int getVoteCount() {
        return voteCount != null ? voteCount : 0;
    }
}
