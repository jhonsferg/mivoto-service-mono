package pe.com.mivoto.service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.mivoto.service.domain.enums.AuditAction;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    private Long id;
    private Long userId;
    private AuditAction action;
    private String entity;
    private Long entityId;
    private String description;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime timestamp;
    private String metadata;

    public boolean isSecurityRelated() {
        return action == AuditAction.LOGIN ||
                action == AuditAction.LOGOUT ||
                action == AuditAction.LOGIN_FAILED ||
                action == AuditAction.UNAUTHORIZED_ACCESS;
    }

    public boolean isVoteRelated() {
        return action == AuditAction.VOTE_CAST ||
                action == AuditAction.VOTE_VERIFIED ||
                action == AuditAction.VOTE_REJECTED;
    }

    public boolean isCritical() {
        return action == AuditAction.ELECTION_CLOSED ||
                action == AuditAction.ELECTION_CANCELLED ||
                action == AuditAction.UNAUTHORIZED_ACCESS ||
                action == AuditAction.DATA_MODIFIED;
    }

    public String getAuditTrailKey() {
        return String.format("%s-%s-%d-%d", action, entity, entityId, timestamp.toEpochSecond(java.time.ZoneOffset.UTC));
    }
}
