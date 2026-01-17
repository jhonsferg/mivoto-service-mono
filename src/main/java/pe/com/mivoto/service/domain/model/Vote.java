package pe.com.mivoto.service.domain.model;

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
public class Vote {

    private Long id;
    private Long userId;
    private Long electionId;
    private Long candidateId;
    private String voteHash;
    private VoteStatus status;
    private Boolean verified;
    private String verificationCode;

    private LocalDateTime votedAt;
    private LocalDateTime verifiedAt;
    private String ipAddress;
    private String userAgent;

    public boolean isPending() {
        return status == VoteStatus.PENDING;
    }

    public boolean isConfirmed() {
        return status == VoteStatus.CONFIRMED;
    }

    public boolean isRejected() {
        return status == VoteStatus.REJECTED;
    }

    public void confirm() {
        this.status = VoteStatus.CONFIRMED;
        this.verified = true;
        this.verifiedAt = LocalDateTime.now();
    }

    public void reject() {
        this.status = VoteStatus.REJECTED;
        this.verifiedAt = LocalDateTime.now();
    }

    public boolean needsVerification() {
        return !verified && status == VoteStatus.PENDING;
    }

    public String generateVoteHash() {
        return String.format("%d-%d-%d-%d", userId, electionId, candidateId, votedAt.toEpochSecond(java.time.ZoneOffset.UTC));
    }
}
