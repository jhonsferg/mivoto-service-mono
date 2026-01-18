package pe.com.mivoto.service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.mivoto.service.domain.enums.AuditAction;

import java.time.LocalDateTime;

/**
 * Represents an audit log entry for tracking system activities.
 * Captures information about which user performed what action, when, and from
 * where.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    /**
     * Unique identifier for the audit log entry.
     */
    private Long id;

    /**
     * ID of the user who performed the action.
     */
    private Long userId;

    /**
     * The specific action that was performed.
     */
    private AuditAction action;

    /**
     * The type of entity involved in the action (e.g., "Election", "User").
     */
    private String entity;

    /**
     * The ID of the specific entity involved.
     */
    private Long entityId;

    /**
     * A human-readable description of the activity.
     */
    private String description;

    /**
     * IP address from which the action was initiated.
     */
    private String ipAddress;

    /**
     * User agent of the client used to perform the action.
     */
    private String userAgent;

    /**
     * Timestamp when the action occurred.
     */
    private LocalDateTime timestamp;

    /**
     * Additional contextual data in JSON or structured format.
     */
    private String metadata;

    /**
     * Checks if the action is related to system security (login, logout, etc.).
     *
     * @return true if security related, false otherwise.
     */
    public boolean isSecurityRelated() {
        return action == AuditAction.LOGIN ||
                action == AuditAction.LOGOUT ||
                action == AuditAction.LOGIN_FAILED ||
                action == AuditAction.UNAUTHORIZED_ACCESS;
    }

    /**
     * Checks if the action is related to voting activities.
     *
     * @return true if vote related, false otherwise.
     */
    public boolean isVoteRelated() {
        return action == AuditAction.VOTE_CAST ||
                action == AuditAction.VOTE_VERIFIED ||
                action == AuditAction.VOTE_REJECTED;
    }

    /**
     * Checks if the action is considered critical for system integrity or state.
     *
     * @return true if critical, false otherwise.
     */
    public boolean isCritical() {
        return action == AuditAction.ELECTION_CLOSED ||
                action == AuditAction.ELECTION_CANCELLED ||
                action == AuditAction.UNAUTHORIZED_ACCESS ||
                action == AuditAction.DATA_MODIFIED;
    }

    /**
     * Generates a unique key for identifying this audit entry in a trail.
     *
     * @return A formatted string key.
     */
    public String getAuditTrailKey() {
        return String.format("%s-%s-%d-%d", action, entity, entityId,
                timestamp.toEpochSecond(java.time.ZoneOffset.UTC));
    }
}
