package pe.com.mivoto.service.application.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.com.mivoto.service.domain.enums.AuditAction;
import pe.com.mivoto.service.domain.model.AuditLog;
import pe.com.mivoto.service.domain.ports.out.AuditRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Application service for managing Audit Logs.
 * Implements the {@link pe.com.mivoto.service.domain.ports.in.AuditUseCase} to
 * provides
 * functionality for logging system events and retrieving audit trails.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuditService implements pe.com.mivoto.service.domain.ports.in.AuditUseCase {
    private final AuditRepository auditRepository;
    private final ObjectMapper objectMapper;

    /**
     * Logs a specialized system action with metadata.
     * Serializes metadata to JSON before persistence.
     *
     * @param userId      The user ID (optional).
     * @param action      The specific action type.
     * @param entity      The target entity.
     * @param entityId    The target entity ID.
     * @param description Brief description of the event.
     * @param ipAddress   Client IP address.
     * @param userAgent   Client User-Agent string.
     * @param metadata    Additional context data map.
     * @return The persisted AuditLog.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AuditLog logAction(Long userId, AuditAction action, String entity, Long entityId, String description,
            String ipAddress, String userAgent, Map<String, Object> metadata) {

        AuditLog auditLog = AuditLog.builder()
                .userId(userId)
                .action(action)
                .entity(entity)
                .entityId(entityId)
                .description(description)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .timestamp(LocalDateTime.now())
                .metadata(serializeMetadata(metadata))
                .build();

        AuditLog saved = this.auditRepository.save(auditLog);

        log.info("Auditoría registrada - Usuario: {}, Acción: {}, Entidad: {}/{}", userId, action, entity, entityId);
        return saved;
    }

    @Override
    public AuditLog logAction(Long userId, AuditAction action, String entity, Long entityId, String description) {
        return logAction(userId, action, entity, entityId, description, null, null, null);
    }

    /**
     * Logs a successful user login event.
     *
     * @param userId    The user ID.
     * @param ipAddress The source IP address.
     */
    public void logSuccessfulLogin(Long userId, String ipAddress) {
        logAction(userId, AuditAction.LOGIN, "User", userId, "Inicio de sesión exitoso", ipAddress, null, null);
    }

    /**
     * Logs a failed login attempt.
     *
     * @param username  The attempted username.
     * @param ipAddress The source IP address.
     */
    public void logFailedLogin(String username, String ipAddress) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("username", username);
        logAction(null, AuditAction.LOGIN_FAILED, "User", null, "Intento de inicio de sesión fallido", ipAddress, null,
                metadata);
    }

    /**
     * Logs a user logout event.
     *
     * @param userId The user ID.
     */
    public void logLogout(Long userId) {
        logAction(userId, AuditAction.LOGOUT, "User", userId, "Cierre de sesión", null, null, null);
    }

    /**
     * Logs a password change event.
     *
     * @param userId The user ID.
     */
    public void logPasswordChange(Long userId) {
        logAction(userId, AuditAction.PASSWORD_CHANGED, "User", userId, "Contraseña cambiada", null, null, null);
    }

    /**
     * Logs a vote cast event.
     *
     * @param userId      The user ID.
     * @param electionId  The election ID.
     * @param candidateId The candidate ID.
     */
    public void logVoteCast(Long userId, Long electionId, Long candidateId) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("electionId", electionId);
        metadata.put("candidateId", candidateId);
        logAction(userId, AuditAction.VOTE_CAST, "Vote", null, "Voto emitido", null, null, metadata);
    }

    /**
     * Logs a vote confirmation.
     *
     * @param voteId   The ID of the vote.
     * @param voteHash The cryptographic hash of the vote.
     */
    public void logVoteVerification(Long voteId, String voteHash) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("voteHash", voteHash);
        logAction(null, AuditAction.VOTE_VERIFIED, "Vote", voteId, "Voto verificado", null, null, metadata);
    }

    /**
     * Logs an invalidation of a vote.
     *
     * @param voteId The ID of the vote.
     * @param reason The reason why the vote was invalidated.
     */
    public void logVoteInvalidation(Long voteId, String reason) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("reason", reason);
        logAction(null, AuditAction.VOTE_REJECTED, "Vote", voteId, "Voto invalidado", null, null, metadata);
    }

    /**
     * Logs the creation of a new election.
     *
     * @param electionId The ID of the created election.
     * @param userId     The ID of the user who created it.
     */
    public void logElectionCreated(Long electionId, Long userId) {
        logAction(userId, AuditAction.ELECTION_CREATED, "Election", electionId, "Elección creada", null, null, null);
    }

    /**
     * Logs an update to an election.
     *
     * @param electionId The ID of the updated election.
     */
    public void logElectionUpdated(Long electionId) {
        logAction(null, AuditAction.ELECTION_UPDATED, "Election", electionId, "Elección actualizada", null, null, null);
    }

    /**
     * Logs the start of an election.
     *
     * @param electionId The ID of the election that started.
     */
    public void logElectionStarted(Long electionId) {
        logAction(null, AuditAction.ELECTION_STARTED, "Election", electionId, "Elección iniciada", null, null, null);
    }

    /**
     * Logs the closing of an election.
     *
     * @param electionId The ID of the closed election.
     */
    public void logElectionClosed(Long electionId) {
        logAction(null, AuditAction.ELECTION_CLOSED, "Election", electionId, "Elección cerrada", null, null, null);
    }

    /**
     * Logs the cancellation of an election.
     *
     * @param electionId The ID of the cancelled election.
     * @param reason     The reason for cancellation.
     */
    public void logElectionCancelled(Long electionId, String reason) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("reason", reason);
        logAction(null, AuditAction.ELECTION_CANCELLED, "Election", electionId, "Elección cancelada", null, null,
                metadata);
    }

    /**
     * Logs when a candidate is added to an election.
     *
     * @param candidateId The ID of the candidate.
     * @param electionId  The ID of the election.
     */
    public void logCandidateAdded(Long candidateId, Long electionId) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("electionId", electionId);
        logAction(null, AuditAction.CANDIDATE_ADDED, "Candidate", candidateId, "Candidato agregado", null, null,
                metadata);
    }

    /**
     * Logs when candidate information is updated.
     *
     * @param candidateId The ID of the candidate.
     */
    public void logCandidateUpdated(Long candidateId) {
        logAction(null, AuditAction.CANDIDATE_UPDATED, "Candidate", candidateId, "Candidato actualizado", null, null,
                null);
    }

    /**
     * Logs when a candidate is deleted.
     *
     * @param candidateId The ID of the candidate.
     */
    public void logCandidateDeleted(Long candidateId) {
        logAction(null, AuditAction.CANDIDATE_REMOVED, "Candidate", candidateId, "Candidato eliminado", null, null,
                null);
    }

    /**
     * Logs when a candidate is activated.
     *
     * @param candidateId The ID of the candidate.
     */
    public void logCandidateActivated(Long candidateId) {
        logAction(null, AuditAction.CANDIDATE_UPDATED, "Candidate", candidateId, "Candidato activado", null, null,
                null);
    }

    /**
     * Logs when a candidate is deactivated.
     *
     * @param candidateId The ID of the candidate.
     */
    public void logCandidateDeactivated(Long candidateId) {
        logAction(null, AuditAction.CANDIDATE_UPDATED, "Candidate", candidateId, "Candidato desactivado", null, null,
                null);
    }

    /**
     * Logs the creation of a new user.
     *
     * @param userId The ID of the new user.
     */
    public void logUserCreated(Long userId) {
        logAction(null, AuditAction.USER_CREATED, "User", userId, "Usuario creado", null, null, null);
    }

    /**
     * Logs an update to user profile or information.
     *
     * @param userId The ID of the user.
     */
    public void logUserUpdated(Long userId) {
        logAction(userId, AuditAction.USER_UPDATED, "User", userId, "Usuario actualizado", null, null, null);
    }

    /**
     * Logs the deletion of a user.
     *
     * @param userId The ID of the deleted user.
     */
    public void logUserDeleted(Long userId) {
        logAction(null, AuditAction.USER_DELETED, "User", userId, "Usuario eliminado", null, null, null);
    }

    /**
     * Logs the activation of a user account.
     *
     * @param userId The ID of the user.
     */
    public void logUserActivated(Long userId) {
        logAction(null, AuditAction.USER_ACTIVATED, "User", userId, "Usuario activado", null, null, null);
    }

    /**
     * Logs the deactivation of a user account.
     *
     * @param userId The ID of the user.
     */
    public void logUserDeactivated(Long userId) {
        logAction(null, AuditAction.USER_DEACTIVATED, "User", userId, "Usuario desactivado", null, null, null);
    }

    /**
     * Logs an unauthorized access attempt to a restricted resource.
     *
     * @param userId    The ID of the user (if authenticated).
     * @param resource  The resource identifier.
     * @param ipAddress The source IP address.
     */
    public void logUnauthorizedAccess(Long userId, String resource, String ipAddress) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("resource", resource);
        logAction(userId, AuditAction.UNAUTHORIZED_ACCESS, "Security", null, "Intento de acceso no autorizado",
                ipAddress, null, metadata);
    }

    /**
     * Logs when an entity's data is accessed.
     *
     * @param userId   The ID of the user who accessed the data.
     * @param entity   The type of entity.
     * @param entityId The ID of the entity.
     */
    public void logDataAccessed(Long userId, String entity, Long entityId) {
        logAction(userId, AuditAction.DATA_ACCESSED, entity, entityId, "Datos accedidos", null, null, null);
    }

    /**
     * Logs when an entity's data is modified.
     *
     * @param userId   The ID of the user who modified the data.
     * @param entity   The type of entity.
     * @param entityId The ID of the entity.
     */
    public void logDataModified(Long userId, String entity, Long entityId) {
        logAction(userId, AuditAction.DATA_MODIFIED, entity, entityId, "Datos modificados", null, null, null);
    }

    /**
     * Retrieves all audit logs.
     *
     * @return List of all audit logs.
     */
    public List<AuditLog> getAllAuditLogs() {
        return this.auditRepository.findAll();
    }

    /**
     * Retrieves the audit trail for a specific user.
     *
     * @param userId The ID of the user.
     * @return List of audit logs associated with the user.
     */
    public List<AuditLog> getAuditTrailByUser(Long userId) {
        return this.auditRepository.findByUserId(userId);
    }

    /**
     * Retrieves the audit trail for a specific entity.
     *
     * @param entity   The entity type name.
     * @param entityId The ID of the entity.
     * @return List of audit logs for the entity.
     */
    public List<AuditLog> getAuditTrailByEntity(String entity, Long entityId) {
        return this.auditRepository.findByEntityAndEntityId(entity, entityId);
    }

    /**
     * Retrieves all audit logs for a specific action type.
     *
     * @param action The audit action to filter by.
     * @return List of matching audit logs.
     */
    public List<AuditLog> getAuditTrailByAction(AuditAction action) {
        return this.auditRepository.findByAction(action);
    }

    /**
     * Retrieves audit logs within a specified date range.
     *
     * @param startDate The start of the range.
     * @param endDate   The end of the range.
     * @return List of audit logs in the range.
     */
    public List<AuditLog> getAuditTrailByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return this.auditRepository.findByTimestampBetween(startDate, endDate);
    }

    /**
     * Retrieves all critical audit logs.
     *
     * @return List of critical audit logs.
     */
    public List<AuditLog> getCriticalAuditLogs() {
        List<AuditLog> allLogs = this.auditRepository.findAll();
        return allLogs.stream()
                .filter(AuditLog::isCritical)
                .toList();
    }

    /**
     * Retrieves all security-related audit logs.
     *
     * @return List of security audit logs.
     */
    public List<AuditLog> getSecurityAuditLogs() {
        List<AuditLog> allLogs = this.auditRepository.findAll();
        return allLogs.stream()
                .filter(AuditLog::isSecurityRelated)
                .toList();
    }

    /**
     * Retrieves all vote-related audit logs.
     *
     * @return List of voting audit logs.
     */
    public List<AuditLog> getVotingAuditLogs() {
        List<AuditLog> allLogs = this.auditRepository.findAll();
        return allLogs.stream()
                .filter(AuditLog::isVoteRelated)
                .toList();
    }

    /**
     * Generates a comprehensive audit report for a given date range.
     *
     * @param startDate The start of the reporting period.
     * @param endDate   The end of the reporting period.
     * @return The generated AuditReport containing statistics and recent logs.
     */
    @Override
    public AuditReport generateAuditReport(LocalDateTime startDate, LocalDateTime endDate) {
        List<AuditLog> logs = getAuditTrailByDateRange(startDate, endDate);

        long totalActions = logs.size();
        long totalUsers = logs.stream()
                .map(AuditLog::getUserId)
                .filter(Objects::nonNull)
                .distinct()
                .count();
        long criticalActions = logs.stream()
                .filter(AuditLog::isCritical)
                .count();
        long securityIncidents = logs.stream()
                .filter(AuditLog::isSecurityRelated)
                .count();

        List<AuditLog> recentLogs = logs.stream()
                .sorted((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()))
                .limit(50)
                .toList();

        return new AuditReport(
                totalActions,
                totalUsers,
                criticalActions,
                securityIncidents,
                recentLogs);
    }

    /**
     * Serializes metadata map to JSON string.
     *
     * @param metadata The metadata map.
     * @return JSON string or null if empty/error.
     */
    private String serializeMetadata(Map<String, Object> metadata) {
        if (metadata == null || metadata.isEmpty()) {
            return null;
        }

        try {
            return this.objectMapper.writeValueAsString(metadata);
        } catch (Exception e) {
            log.error("Error serializando metadata", e);
            return null;
        }
    }
}
