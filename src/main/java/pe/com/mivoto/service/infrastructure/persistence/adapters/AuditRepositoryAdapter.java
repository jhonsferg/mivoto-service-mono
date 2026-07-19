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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    /**
     * Saves an audit log entry.
     *
     * @param auditLog The audit log to save.
     * @return The saved audit log.
     */
    @Override
    public AuditLog save(AuditLog auditLog) {
        log.debug("Guardando log de auditoría: {}", auditLog.getAction());
        AuditLogEntity entity = auditEntityMapper.toEntity(auditLog);
        AuditLogEntity saved = jpaAuditLogRepository.save(entity);
        return auditEntityMapper.toDomain(saved);
    }

    /**
     * Finds an audit log by its ID.
     *
     * @param id The ID of the log.
     * @return Optional containing the log if found.
     */
    @Override
    public Optional<AuditLog> findById(Long id) {
        return jpaAuditLogRepository.findById(id)
                .map(auditEntityMapper::toDomain);
    }

    /**
     * Retrieves all audit logs.
     *
     * @return List of all logs.
     */
    @Override
    public List<AuditLog> findAll() {
        return jpaAuditLogRepository.findAll().stream()
                .map(auditEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds audit logs by user ID.
     *
     * @param userId The user ID.
     * @return List of logs for the user.
     */
    @Override
    public List<AuditLog> findByUserId(Long userId) {
        return jpaAuditLogRepository.findByUserId(userId).stream()
                .map(auditEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds audit logs by action type.
     *
     * @param action The action type.
     * @return List of matching logs.
     */
    @Override
    public List<AuditLog> findByAction(AuditAction action) {
        return jpaAuditLogRepository.findByAction(action).stream()
                .map(auditEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds logs associated with a specific entity.
     *
     * @param entity   The entity name.
     * @param entityId The entity ID.
     * @return List of matching logs.
     */
    @Override
    public List<AuditLog> findByEntityAndEntityId(String entity, Long entityId) {
        return jpaAuditLogRepository.findByEntityAndEntityId(entity, entityId).stream()
                .map(auditEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds logs within a date range.
     *
     * @param startDate The start date.
     * @param endDate   The end date.
     * @return List of logs in the range.
     */
    @Override
    public List<AuditLog> findByTimestampBetween(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);
        return jpaAuditLogRepository.findByTimestampBetween(start, end).stream()
                .map(auditEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the most recent audit logs.
     *
     * @param limit The maximum number of logs to return.
     * @return List of recent logs.
     */
    @Override
    public List<AuditLog> findRecentLogs(int limit) {
        return jpaAuditLogRepository.findRecentLogs(limit).stream()
                .map(auditEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds logs by IP address.
     *
     * @param ipAddress The IP address.
     * @return List of matching logs.
     */
    @Override
    public List<AuditLog> findByIpAddress(String ipAddress) {
        return jpaAuditLogRepository.findByIpAddress(ipAddress).stream()
                .map(auditEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Counts logs by user ID.
     *
     * @param userId The user ID.
     * @return The count of logs.
     */
    @Override
    public Long countByUserId(Long userId) {
        return jpaAuditLogRepository.countByUserId(userId);
    }

    /**
     * Counts logs by action type.
     *
     * @param action The action type.
     * @return The count of logs.
     */
    @Override
    public Long countByAction(AuditAction action) {
        return jpaAuditLogRepository.countByAction(action);
    }

    /**
     * Deletes logs older than a specific date.
     *
     * @param beforeDate The threshold date.
     * @return The number of deleted logs.
     */
    @Override
    public Long deleteOldLogs(LocalDateTime beforeDate) {
        return jpaAuditLogRepository.deleteByTimestampBefore(beforeDate);
    }
}
