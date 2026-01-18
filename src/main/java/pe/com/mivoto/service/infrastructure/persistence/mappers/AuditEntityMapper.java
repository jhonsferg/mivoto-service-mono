package pe.com.mivoto.service.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.AuditLog;
import pe.com.mivoto.service.infrastructure.persistence.entities.AuditLogEntity;

/**
 * Mapper for Audit Log.
 * Converts between AuditLog domain model and AuditLogEntity.
 */
@Component
public class AuditEntityMapper {

    /**
     * Converts AuditLogEntity to AuditLog domain model.
     *
     * @param entity The AuditLogEntity.
     * @return The AuditLog domain model.
     */
    public AuditLog toDomain(AuditLogEntity entity) {
        if (entity == null) {
            return null;
        }

        return AuditLog.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .action(entity.getAction())
                .entity(entity.getEntity())
                .entityId(entity.getEntityId())
                .description(entity.getDescription())
                .ipAddress(entity.getIpAddress())
                .userAgent(entity.getUserAgent())
                .timestamp(entity.getTimestamp())
                .metadata(entity.getMetadata())
                .build();
    }

    /**
     * Converts AuditLog domain model to AuditLogEntity.
     *
     * @param domain The AuditLog domain model.
     * @return The AuditLogEntity.
     */
    public AuditLogEntity toEntity(AuditLog domain) {
        if (domain == null) {
            return null;
        }

        return AuditLogEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .action(domain.getAction())
                .entity(domain.getEntity())
                .entityId(domain.getEntityId())
                .description(domain.getDescription())
                .ipAddress(domain.getIpAddress())
                .userAgent(domain.getUserAgent())
                .timestamp(domain.getTimestamp())
                .metadata(domain.getMetadata())
                .build();
    }
}
