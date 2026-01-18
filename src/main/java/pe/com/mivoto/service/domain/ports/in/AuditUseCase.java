package pe.com.mivoto.service.domain.ports.in;

import pe.com.mivoto.service.domain.enums.AuditAction;
import pe.com.mivoto.service.domain.model.AuditLog;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Input port for audit logging and reporting operations.
 * Provides methods for recording activities and retrieving historical audit
 * data.
 */
public interface AuditUseCase {
    /**
     * Records a specific action performed in the system.
     *
     * @param userId      ID of the user who performed the action.
     * @param action      The type of action performed.
     * @param entity      The type of entity affected.
     * @param entityId    The specific ID of the affected entity.
     * @param description A descriptive message of the activity.
     * @return The created AuditLog entry.
     */
    AuditLog logAction(Long userId, AuditAction action, String entity, Long entityId, String description);

    /**
     * Retrieves all audit logs for a specific user.
     *
     * @param userId The user ID.
     * @return List of audit logs.
     */
    List<AuditLog> getAuditTrailByUser(Long userId);

    /**
     * Retrieves all audit logs affecting a specific entity.
     *
     * @param entity   The entity type.
     * @param entityId The entity ID.
     * @return List of audit logs.
     */
    List<AuditLog> getAuditTrailByEntity(String entity, Long entityId);

    /**
     * Retrieves all audit logs for a specific type of action.
     *
     * @param action The audit action.
     * @return List of audit logs.
     */
    List<AuditLog> getAuditTrailByAction(AuditAction action);

    /**
     * Retrieves audit logs within a specific date range.
     *
     * @param startDate Start of the range.
     * @param endDate   End of the range.
     * @return List of audit logs.
     */
    List<AuditLog> getAuditTrailByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Retrieves logs considered critical for system security or integrity.
     *
     * @return List of critical audit logs.
     */
    List<AuditLog> getCriticalAuditLogs();

    /**
     * Retrieves logs related specifically to security events (login, unauthorized
     * access).
     *
     * @return List of security-related audit logs.
     */
    List<AuditLog> getSecurityAuditLogs();

    /**
     * Retrieves logs related specifically to voting activities.
     *
     * @return List of voting-related audit logs.
     */
    List<AuditLog> getVotingAuditLogs();

    /**
     * Processes audit data to generate a statistical report.
     *
     * @param startDate Start date for the report data.
     * @param endDate   End date for the report data.
     * @return An AuditReport containing aggregated statistics.
     */
    AuditReport generateAuditReport(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Record containing aggregated audit statistics for reporting.
     *
     * @param totalActions      Total number of actions recorded.
     * @param totalUsers        Total unique users identified in the logs.
     * @param criticalActions   Number of critical actions detected.
     * @param securityIncidents Number of security-related incidents.
     * @param recentLogs        List of the most recent audit logs.
     */
    record AuditReport(
            Long totalActions,
            Long totalUsers,
            Long criticalActions,
            Long securityIncidents,
            List<AuditLog> recentLogs) {
    }
}