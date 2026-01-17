package pe.com.mivoto.service.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.mivoto.service.domain.enums.ElectionStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectionResponse {
    private Long id;
    private String title;
    private String description;
    private ElectionStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer maxVotesPerUser;
    private Boolean allowsBlankVote;
    private Boolean requiresVerification;
    private List<CandidateDto> candidates;
    private Boolean hasVoted;
    private LocalDateTime createdAt;
}
