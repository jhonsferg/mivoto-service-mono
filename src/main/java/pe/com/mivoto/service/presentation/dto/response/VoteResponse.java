package pe.com.mivoto.service.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.mivoto.service.domain.enums.VoteStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteResponse {
    private Long id;
    private Long electionId;
    private Long candidateId;
    private String voteHash;
    private VoteStatus status;
    private Boolean verified;
    private LocalDateTime votedAt;
    private String verificationCode;
}
