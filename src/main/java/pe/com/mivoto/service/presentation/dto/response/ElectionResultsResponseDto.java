package pe.com.mivoto.service.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for election results.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectionResultsResponseDto {

    /**
     * ID of the election.
     */
    private Long electionId;

    /**
     * Title of the election.
     */
    private String electionTitle;

    /**
     * Total number of votes cast.
     */
    private Long totalVotes;

    /**
     * Detailed results per candidate.
     */
    private List<CandidateResultDto> results;

    /**
     * Result details for a single candidate.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CandidateResultDto {

        /**
         * ID of the candidate.
         */
        private Long candidateId;

        /**
         * Name of the candidate.
         */
        private String candidateName;

        /**
         * Political party.
         */
        private String party;

        /**
         * Ballot number.
         */
        private Integer number;

        /**
         * Total votes received by this candidate.
         */
        private Long votes;

        /**
         * Percentage of total votes.
         */
        private Double percentage;
    }
}
