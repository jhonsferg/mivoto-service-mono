package pe.com.mivoto.service.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDto {
    private Long id;
    private Long electionId;
    private Integer number;
    private String name;
    private String party;
    private String description;
    private String photoUrl;
    private Boolean active;
    private Integer voteCount;
}
