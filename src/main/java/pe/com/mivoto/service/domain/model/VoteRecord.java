package pe.com.mivoto.service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteRecord {

    private Long id;
    private Long voteId;
    private Long userId;
    private Long electionId;
    private String voteHash;
    private LocalDateTime timestamp;
    private Boolean verified;
    private String blockchainHash;

    public boolean isVerified() {
        return verified != null && verified;
    }

    public String getAuditKey() {
        return String.format("VOTE-%d-%s", voteId, voteHash);
    }

    public boolean isValid() {
        return voteHash != null && !voteHash.isEmpty() && timestamp != null;
    }
}
