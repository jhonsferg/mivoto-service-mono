package pe.com.mivoto.service.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Candidate details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDto {

    /**
     * Unique identifier of the candidate.
     */
    private Long id;

    /**
     * ID of the election the candidate belongs to.
     */
    private Long electionId;

    /**
     * Candidate's ballot number.
     */
    private Integer number;

    /**
     * Full name of the candidate.
     */
    private String name;

    /**
     * Political party.
     */
    private String party;

    /**
     * Brief description or biography.
     */
    private String description;

    /**
     * URL to the candidate's photo.
     */
    private String photoUrl;

    /**
     * Whether the candidate is active.
     */
    private Boolean active;

    /**
     * Current vote count for this candidate.
     */
    private Integer voteCount;
}
