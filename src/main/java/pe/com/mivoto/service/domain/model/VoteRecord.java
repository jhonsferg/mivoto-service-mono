package pe.com.mivoto.service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents a historical or persisted record of a vote, often used for
 * auditing or blockchain integration.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteRecord {

    /**
     * Unique identifier for the vote record.
     */
    private Long id;

    /**
     * Reference to the original vote ID.
     */
    private Long voteId;

    /**
     * ID of the user who cast the vote.
     */
    private Long userId;

    /**
     * ID of the election.
     */
    private Long electionId;

    /**
     * Cryptographic hash of the vote data.
     */
    private String voteHash;

    /**
     * Timestamp when the record was created.
     */
    private LocalDateTime timestamp;

    /**
     * Whether the vote has been verified.
     */
    private Boolean verified;

    /**
     * Hash corresponding to the transaction in a blockchain (if applicable).
     */
    private String blockchainHash;

    /**
     * Checks if the vote has been officially verified.
     *
     * @return true if verified, false otherwise.
     */
    public boolean isVerified() {
        return verified != null && verified;
    }

    /**
     * Generates a unique key for identifying this vote record in audit trails.
     *
     * @return A formatted audit key.
     */
    public String getAuditKey() {
        return String.format("VOTE-%d-%s", voteId, voteHash);
    }

    /**
     * Validates if the record has the minimum required data for integrity.
     *
     * @return true if data is complete and valid.
     */
    public boolean isValid() {
        return voteHash != null && !voteHash.isEmpty() && timestamp != null;
    }
}
