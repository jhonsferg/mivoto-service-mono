package pe.com.mivoto.service.application.usecases.audit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.AuditService;
import pe.com.mivoto.service.domain.enums.AuditAction;
import pe.com.mivoto.service.domain.model.AuditLog;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Use case implementation for retrieving audit trails.
 * Wraps {@link pe.com.mivoto.service.application.services.AuditService}
 * retrieval logic.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GetAuditTrailUseCaseImpl {
    private final AuditService auditService;

    /**
     * executes audit retrieval by user.
     *
     * @param userId The user ID.
     * @return List of audit logs.
     */
    public List<AuditLog> executeByUser(Long userId) {
        log.info("Ejecutando caso de uso: GetAuditTrail por usuario: {}", userId);
        return this.auditService.getAuditTrailByUser(userId);
    }

    /**
     * Executes audit retrieval by entity.
     *
     * @param entity   The entity type.
     * @param entityId The entity ID.
     * @return List of audit logs.
     */
    public List<AuditLog> executeByEntity(String entity, Long entityId) {
        log.info("Ejecutando caso de uso: GetAuditTrail por entidad: {}/{}", entity, entityId);
        return this.auditService.getAuditTrailByEntity(entity, entityId);
    }

    /**
     * Executes audit retrieval by action.
     *
     * @param action The audit action.
     * @return List of audit logs.
     */
    public List<AuditLog> executeByAction(AuditAction action) {
        log.info("Ejecutando caso de uso: GetAuditTrail por acción: {}", action);
        return this.auditService.getAuditTrailByAction(action);
    }

    /**
     * Executes audit retrieval by date range.
     *
     * @param startDate Start date.
     * @param endDate   End date.
     * @return List of audit logs.
     */
    public List<AuditLog> executeByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Ejecutando caso de uso: GetAuditTrail por rango de fechas");
        return this.auditService.getAuditTrailByDateRange(startDate, endDate);
    }

    /**
     * Executes retrieval of critical audit logs.
     *
     * @return List of critical audit logs.
     */
    public List<AuditLog> executeCriticalLogs() {
        log.info("Ejecutando caso de uso: GetAuditTrail logs críticos");
        return this.auditService.getCriticalAuditLogs();
    }
}
