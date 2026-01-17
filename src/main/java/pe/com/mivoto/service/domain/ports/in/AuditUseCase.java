package pe.com.mivoto.service.domain.ports.in;

import pe.com.mivoto.service.domain.enums.AuditAction;
import pe.com.mivoto.service.domain.model.AuditLog;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditUseCase {
    AuditLog logAction(Long userId, AuditAction action, String entity, Long entityId, String description);

    List<AuditLog> getAuditTrailByUser(Long userId);

    List<AuditLog> getAuditTrailByEntity(String entity, Long entityId);

    List<AuditLog> getAuditTrailByAction(AuditAction action);

    List<AuditLog> getAuditTrailByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<AuditLog> getCriticalAuditLogs();

    List<AuditLog> getSecurityAuditLogs();

    List<AuditLog> getVotingAuditLogs();

    AuditReport generateAuditReport(LocalDateTime startDate, LocalDateTime endDate);

    record AuditReport(
            Long totalActions,
            Long totalUsers,
            Long criticalActions,
            Long securityIncidents,
            List<AuditLog> recentLogs
    ) {
    }
}