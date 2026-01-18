package pe.com.mivoto.service.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.mivoto.service.domain.enums.VoteStatus;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Vote confirmation responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteResponseDto {

    /**
     * Unique identifier of the vote.
     */
    private Long id;

    /**
     * ID of the election.
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
     * Current status of the vote (e.g., PENDING, CONFIRMED).
     */
    private VoteStatus status;

    /**
     * Whether the vote has been verified.
     */
    private Boolean verified;

    /**
     * Timestamp when the vote was cast.
     */
    private LocalDateTime votedAt;

    /**
     * Verification code provided to the user.
     */
    private String verificationCode;
}
