package pe.com.mivoto.service.application.usecases.audit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.AuditService;
import pe.com.mivoto.service.domain.enums.AuditAction;
import pe.com.mivoto.service.domain.model.AuditLog;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateAuditLogUseCaseImpl {
    private final AuditService auditService;

    public AuditLog execute(Long userId, AuditAction action, String entity, Long entityId, String description, String ipAddress, String userAgent, Map<String, Object> metadata) {
        log.debug("Ejecutando caso de uso: CreateAuditLog - Acción: {}", action);
        return this.auditService.logAction(userId, action, entity, entityId, description, ipAddress, userAgent, metadata);
    }
}
