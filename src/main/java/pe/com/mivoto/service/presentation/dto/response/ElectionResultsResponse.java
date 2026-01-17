package pe.com.mivoto.service.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectionResultsResponse {
    private Long electionId;
    private String electionTitle;
    private Long totalVotes;
    private List<CandidateResultDto> results;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CandidateResultDto {
        private Long candidateId;
        private String candidateName;
        private String party;
        private Integer number;
        private Long votes;
        private Double percentage;
    }
}
