package pe.com.mivoto.service.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.mivoto.service.domain.enums.ElectionStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for Election details in responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectionResponseDto {

    /**
     * Unique identifier of the election.
     */
    private Long id;

    /**
     * Title of the election.
     */
    private String title;

    /**
     * Description of the election.
     */
    private String description;

    /**
     * Current status of the election.
     */
    private ElectionStatus status;

    /**
     * Scheduled start date and time.
     */
    private LocalDateTime startDate;

    /**
     * Scheduled end date and time.
     */
    private LocalDateTime endDate;

    /**
     * Maximum votes allowed per user.
     */
    private Integer maxVotesPerUser;

    /**
     * Whether blank votes are permitted.
     */
    private Boolean allowsBlankVote;

    /**
     * Whether manual verification is required.
     */
    private Boolean requiresVerification;

    /**
     * List of candidates in this election.
     */
    private List<CandidateDto> candidates;

    /**
     * Indicates if the current user has already voted in this election.
     */
    private Boolean hasVoted;

    /**
     * Timestamp when the election was created.
     */
    private LocalDateTime createdAt;
}
