package pe.com.mivoto.service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.mivoto.service.domain.enums.AuditAction;
import pe.com.mivoto.service.infrastructure.persistence.entities.AuditLogEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JpaAuditLogRepository extends JpaRepository<AuditLogEntity, Long> {
    List<AuditLogEntity> findByUserId(Long userId);

    List<AuditLogEntity> findByAction(AuditAction action);

    List<AuditLogEntity> findByEntityAndEntityId(String entity, Long entityId);

    List<AuditLogEntity> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT a FROM AuditLogEntity a ORDER BY a.timestamp DESC")
    List<AuditLogEntity> findAllOrderByTimestampDesc();

    @Query(value = "SELECT * FROM audit_logs ORDER BY timestamp DESC LIMIT :limit", nativeQuery = true)
    List<AuditLogEntity> findRecentLogs(int limit);

    List<AuditLogEntity> findByIpAddress(String ipAddress);

    Long countByUserId(Long userId);

    Long countByAction(AuditAction action);

    @Query("DELETE FROM AuditLogEntity a WHERE a.timestamp < :beforeDate")
    Long deleteByTimestampBefore(LocalDateTime beforeDate);
}
