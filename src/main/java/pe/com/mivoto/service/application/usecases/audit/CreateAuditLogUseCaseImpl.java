package pe.com.mivoto.service.application.usecases.audit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.AuditService;
import pe.com.mivoto.service.domain.enums.AuditAction;
import pe.com.mivoto.service.domain.model.AuditLog;

import java.util.Map;

/**
 * Use case implementation for creating audit logs.
 * Wraps
 * {@link pe.com.mivoto.service.application.services.AuditService#logAction}
 * logic.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CreateAuditLogUseCaseImpl {
    private final AuditService auditService;

    /**
     * Executes the create audit log use case.
     *
     * @param userId      The user ID.
     * @param action      The audit action.
     * @param entity      The entity affected.
     * @param entityId    The entity ID.
     * @param description Description of the action.
     * @param ipAddress   The IP address.
     * @param userAgent   The User-Agent string.
     * @param metadata    Additional metadata.
     * @return The created AuditLog.
     */
    public AuditLog execute(Long userId, AuditAction action, String entity, Long entityId, String description,
                            String ipAddress, String userAgent, Map<String, Object> metadata) {
        log.debug("Ejecutando caso de uso: CreateAuditLog - Acción: {}", action);
        return this.auditService.logAction(userId, action, entity, entityId, description, ipAddress, userAgent,
                metadata);
    }
}
