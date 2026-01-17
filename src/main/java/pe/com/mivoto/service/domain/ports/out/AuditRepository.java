package pe.com.mivoto.service.domain.ports.out;

import pe.com.mivoto.service.domain.enums.AuditAction;
import pe.com.mivoto.service.domain.model.AuditLog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AuditRepository {
    AuditLog save(AuditLog auditLog);

    Optional<AuditLog> findById(Long id);

    List<AuditLog> findAll();

    List<AuditLog> findByUserId(Long userId);

    List<AuditLog> findByAction(AuditAction action);

    List<AuditLog> findByEntityAndEntityId(String entity, Long entityId);

    List<AuditLog> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<AuditLog> findRecentLogs(int limit);

    List<AuditLog> findByIpAddress(String ipAddress);

    Long countByUserId(Long userId);

    Long countByAction(AuditAction action);

    Long deleteOldLogs(LocalDateTime beforeDate);
}
