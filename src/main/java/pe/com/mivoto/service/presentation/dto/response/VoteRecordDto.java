package pe.com.mivoto.service.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteRecordDto {
    private Long id;
    private Long voteId;
    private Long electionId;
    private String voteHash;
    private LocalDateTime timestamp;
    private Boolean verified;
}
