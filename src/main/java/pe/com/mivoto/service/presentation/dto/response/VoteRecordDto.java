package pe.com.mivoto.service.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Vote Record details.
 * Used to represent the historical record of a vote, often for verification
 * purposes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteRecordDto {

    /**
     * Unique identifier of the vote record.
     */
    private Long id;

    /**
     * ID of the original vote.
     */
    private Long voteId;

    /**
     * ID of the election.
     */
    private Long electionId;

    /**
     * Cryptographic hash of the vote.
     */
    private String voteHash;

    /**
     * Timestamp when the vote was recorded.
     */
    private LocalDateTime timestamp;

    /**
     * Whether the vote has been verified.
     */
    private Boolean verified;
}
