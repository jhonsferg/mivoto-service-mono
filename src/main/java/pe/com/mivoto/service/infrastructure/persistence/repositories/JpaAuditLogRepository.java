package pe.com.mivoto.service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.mivoto.service.domain.enums.AuditAction;
import pe.com.mivoto.service.infrastructure.persistence.entities.AuditLogEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * JPA Repository for Audit Logs.
 * Provides database access methods for managing AuditLogEntity.
 */
@Repository
public interface JpaAuditLogRepository extends JpaRepository<AuditLogEntity, Long> {

    /**
     * Finds audit logs by user ID.
     *
     * @param userId The user ID.
     * @return List of audit logs.
     */
    List<AuditLogEntity> findByUserId(Long userId);

    /**
     * Finds audit logs by action type.
     *
     * @param action The audit action.
     * @return List of audit logs.
     */
    List<AuditLogEntity> findByAction(AuditAction action);

    /**
     * Finds audit logs by entity type and ID.
     *
     * @param entity   The entity type.
     * @param entityId The entity ID.
     * @return List of audit logs.
     */
    List<AuditLogEntity> findByEntityAndEntityId(String entity, Long entityId);

    /**
     * Finds audit logs within a date range.
     *
     * @param startDate The start date.
     * @param endDate   The end date.
     * @return List of audit logs.
     */
    List<AuditLogEntity> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Retrieves all audit logs ordered by timestamp descending.
     *
     * @return List of ordered audit logs.
     */
    @Query("SELECT a FROM AuditLogEntity a ORDER BY a.timestamp DESC")
    List<AuditLogEntity> findAllOrderByTimestampDesc();

    /**
     * Retrieves the most recent audit logs with a limit.
     *
     * @param limit The maximum number of logs to retrieve.
     * @return List of recent audit logs.
     */
    @Query(value = "SELECT * FROM audit_logs ORDER BY timestamp DESC LIMIT :limit", nativeQuery = true)
    List<AuditLogEntity> findRecentLogs(int limit);

    /**
     * Finds audit logs by IP address.
     *
     * @param ipAddress The IP address.
     * @return List of audit logs.
     */
    List<AuditLogEntity> findByIpAddress(String ipAddress);

    /**
     * Counts audit logs by user ID.
     *
     * @param userId The user ID.
     * @return The count of logs.
     */
    Long countByUserId(Long userId);

    /**
     * Counts audit logs by action type.
     *
     * @param action The audit action.
     * @return The count of logs.
     */
    Long countByAction(AuditAction action);

    /**
     * Deletes audit logs older than a specific date.
     *
     * @param beforeDate The threshold date.
     * @return The number of deleted logs.
     */
    @Query("DELETE FROM AuditLogEntity a WHERE a.timestamp < :beforeDate")
    Long deleteByTimestampBefore(LocalDateTime beforeDate);
}
