package pe.com.mivoto.service.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.enums.AuditAction;
import pe.com.mivoto.service.domain.model.AuditLog;
import pe.com.mivoto.service.domain.ports.out.AuditRepository;
import pe.com.mivoto.service.infrastructure.persistence.entities.AuditLogEntity;
import pe.com.mivoto.service.infrastructure.persistence.mappers.AuditEntityMapper;
import pe.com.mivoto.service.infrastructure.persistence.repositories.JpaAuditLogRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the Audit Output Port (AuditRepository) using the JPA
 * repository.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuditRepositoryAdapter implements AuditRepository {

    private final JpaAuditLogRepository jpaAuditLogRepository;
    private final AuditEntityMapper auditEntityMapper;

    @Override
    public AuditLog save(AuditLog auditLog) {
        log.debug("Guardando log de auditoría: {}", auditLog.getAction());
        AuditLogEntity entity = auditEntityMapper.toEntity(auditLog);
        AuditLogEntity saved = jpaAuditLogRepository.save(entity);
        return auditEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<AuditLog> findById(Long id) {
        return jpaAuditLogRepository.findById(id)
                .map(auditEntityMapper::toDomain);
    }

    @Override
    public List<AuditLog> findAll() {
        return jpaAuditLogRepository.findAll().stream()
                .map(auditEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditLog> findByUserId(Long userId) {
        return jpaAuditLogRepository.findByUserId(userId).stream()
                .map(auditEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditLog> findByAction(AuditAction action) {
        return jpaAuditLogRepository.findByAction(action).stream()
                .map(auditEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditLog> findByEntityAndEntityId(String entity, Long entityId) {
        return jpaAuditLogRepository.findByEntityAndEntityId(entity, entityId).stream()
                .map(auditEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditLog> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return jpaAuditLogRepository.findByTimestampBetween(startDate, endDate).stream()
                .map(auditEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditLog> findRecentLogs(int limit) {
        return jpaAuditLogRepository.findRecentLogs(limit).stream()
                .map(auditEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditLog> findByIpAddress(String ipAddress) {
        return jpaAuditLogRepository.findByIpAddress(ipAddress).stream()
                .map(auditEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Long countByUserId(Long userId) {
        return jpaAuditLogRepository.countByUserId(userId);
    }

    @Override
    public Long countByAction(AuditAction action) {
        return jpaAuditLogRepository.countByAction(action);
    }

    @Override
    public Long deleteOldLogs(LocalDateTime beforeDate) {
        return jpaAuditLogRepository.deleteByTimestampBefore(beforeDate);
    }
}
