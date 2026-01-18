package pe.com.mivoto.service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.mivoto.service.domain.enums.VoteStatus;

import java.time.LocalDateTime;

/**
 * Represents a single vote cast by a user in an election.
 * Contains information about the voter, election, candidate, and verification
 * status.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    /**
     * Unique identifier for the vote.
     */
    private Long id;

    /**
     * ID of the user who cast the vote.
     */
    private Long userId;

    /**
     * ID of the election the vote belongs to.
     */
    private Long electionId;

    /**
     * ID of the candidate voted for.
     */
    private Long candidateId;

    /**
     * Cryptographic hash of the vote for integrity verification.
     */
    private String voteHash;

    /**
     * Current status of the vote (PENDING, CONFIRMED, REJECTED).
     */
    private VoteStatus status;

    /**
     * Whether the vote has been verified.
     */
    private Boolean verified;

    /**
     * Verification code provided to the voter.
     */
    private String verificationCode;

    /**
     * Timestamp when the vote was cast.
     */
    private LocalDateTime votedAt;

    /**
     * Timestamp when the vote was verified.
     */
    private LocalDateTime verifiedAt;

    /**
     * IP address from which the vote was cast.
     */
    private String ipAddress;

    /**
     * User agent of the client used to cast the vote.
     */
    private String userAgent;

    /**
     * Checks if the vote is currently in PENDING status.
     *
     * @return true if pending, false otherwise.
     */
    public boolean isPending() {
        return status == VoteStatus.PENDING;
    }

    /**
     * Checks if the vote has been confirmed.
     *
     * @return true if confirmed, false otherwise.
     */
    public boolean isConfirmed() {
        return status == VoteStatus.CONFIRMED;
    }

    /**
     * Checks if the vote has been rejected.
     *
     * @return true if rejected, false otherwise.
     */
    public boolean isRejected() {
        return status == VoteStatus.REJECTED;
    }

    /**
     * Transitions the vote status to CONFIRMED and sets the verification timestamp.
     */
    public void confirm() {
        this.status = VoteStatus.CONFIRMED;
        this.verified = true;
        this.verifiedAt = LocalDateTime.now();
    }

    /**
     * Transitions the vote status to REJECTED.
     */
    public void reject() {
        this.status = VoteStatus.REJECTED;
        this.verifiedAt = LocalDateTime.now();
    }

    /**
     * Determines if the vote requires verification processing.
     *
     * @return true if the vote is pending and not yet verified.
     */
    public boolean needsVerification() {
        return !verified && status == VoteStatus.PENDING;
    }

    /**
     * Generates a unique hash string for the vote based on its core attributes.
     * This hash is used for integrity verification.
     *
     * @return A formatted string representation of the vote data.
     */
    public String generateVoteHash() {
        return String.format("%d-%d-%d-%d", userId, electionId, candidateId,
                votedAt.toEpochSecond(java.time.ZoneOffset.UTC));
    }
}
