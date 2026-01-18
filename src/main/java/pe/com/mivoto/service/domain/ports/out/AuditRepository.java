package pe.com.mivoto.service.domain.ports.out;

import pe.com.mivoto.service.domain.enums.AuditAction;
import pe.com.mivoto.service.domain.model.AuditLog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Output port for audit log data access.
 * Defines methods for persistence and retrieval of audit logs.
 */
public interface AuditRepository {

    /**
     * Persists an audit log entry.
     *
     * @param auditLog The audit log to save.
     * @return The saved AuditLog.
     */
    AuditLog save(AuditLog auditLog);

    /**
     * Finds an audit log by its ID.
     *
     * @param id The audit log ID.
     * @return An Optional containing the AuditLog if found.
     */
    Optional<AuditLog> findById(Long id);

    /**
     * Retrieves all audit logs from the repository.
     *
     * @return A list of all audit logs.
     */
    List<AuditLog> findAll();

    /**
     * Finds audit logs associated with a specific user.
     *
     * @param userId The user ID.
     * @return A list of matching audit logs.
     */
    List<AuditLog> findByUserId(Long userId);

    /**
     * Finds audit logs by the type of action performed.
     *
     * @param action The audit action.
     * @return A list of matching audit logs.
     */
    List<AuditLog> findByAction(AuditAction action);

    /**
     * Finds audit logs for a specific entity.
     *
     * @param entity   The entity type.
     * @param entityId The entity ID.
     * @return A list of matching audit logs.
     */
    List<AuditLog> findByEntityAndEntityId(String entity, Long entityId);

    /**
     * Finds audit logs created within a specific time range.
     *
     * @param startDate The start of the range.
     * @param endDate   The end of the range.
     * @return A list of audit logs.
     */
    List<AuditLog> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Retrieves the most recent audit logs up to a specified limit.
     *
     * @param limit The maximum number of logs to retrieve.
     * @return A list of recent audit logs.
     */
    List<AuditLog> findRecentLogs(int limit);

    /**
     * Finds audit logs associated with a specific IP address.
     *
     * @param ipAddress The IP address.
     * @return A list of matching audit logs.
     */
    List<AuditLog> findByIpAddress(String ipAddress);

    /**
     * Counts the number of audit logs for a specific user.
     *
     * @param userId The user ID.
     * @return The count of logs.
     */
    Long countByUserId(Long userId);

    /**
     * Counts the number of audit logs for a specific action.
     *
     * @param action The audit action.
     * @return The count of logs.
     */
    Long countByAction(AuditAction action);

    /**
     * Deletes audit logs created before a specified date.
     *
     * @param beforeDate The cutoff date for deletion.
     * @return The number of deleted logs.
     */
    Long deleteOldLogs(LocalDateTime beforeDate);
}
