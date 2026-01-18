package pe.com.mivoto.service.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.VotingSession;
import pe.com.mivoto.service.domain.ports.out.SessionRepository;
import pe.com.mivoto.service.infrastructure.persistence.entities.VotingSessionEntity;
import pe.com.mivoto.service.infrastructure.persistence.mappers.VotingSessionEntityMapper;
import pe.com.mivoto.service.infrastructure.persistence.repositories.JpaVotingSessionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the Session Output Port (SessionRepository) using the JPA
 * repository.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SessionRepositoryAdapter implements SessionRepository {

    private final JpaVotingSessionRepository jpaVotingSessionRepository;
    private final VotingSessionEntityMapper votingSessionEntityMapper;

    /**
     * Saves a new voting session.
     *
     * @param session The session to save.
     * @return The saved session.
     */
    @Override
    public VotingSession save(VotingSession session) {
        log.debug("Guardando sesión para el usuario: {}", session.getUserId());
        VotingSessionEntity entity = votingSessionEntityMapper.toEntity(session);
        VotingSessionEntity saved = jpaVotingSessionRepository.save(entity);
        return votingSessionEntityMapper.toDomain(saved);
    }

    /**
     * Updates an existing voting session.
     *
     * @param session The session updates.
     * @return The updated session.
     */
    @Override
    public VotingSession update(VotingSession session) {
        log.debug("Actualizando sesión: {}", session.getId());
        VotingSessionEntity entity = votingSessionEntityMapper.toEntity(session);
        VotingSessionEntity updated = jpaVotingSessionRepository.save(entity);
        return votingSessionEntityMapper.toDomain(updated);
    }

    /**
     * Finds a session by ID.
     *
     * @param id The session ID.
     * @return Optional containing the session if found.
     */
    @Override
    public Optional<VotingSession> findById(Long id) {
        return jpaVotingSessionRepository.findById(id)
                .map(votingSessionEntityMapper::toDomain);
    }

    /**
     * Finds a session by its token.
     *
     * @param sessionToken The session token.
     * @return Optional containing the session if found.
     */
    @Override
    public Optional<VotingSession> findBySessionToken(String sessionToken) {
        return jpaVotingSessionRepository.findBySessionToken(sessionToken)
                .map(votingSessionEntityMapper::toDomain);
    }

    /**
     * Finds a session by its refresh token.
     *
     * @param refreshToken The refresh token.
     * @return Optional containing the session if found.
     */
    @Override
    public Optional<VotingSession> findByRefreshToken(String refreshToken) {
        return jpaVotingSessionRepository.findByRefreshToken(refreshToken)
                .map(votingSessionEntityMapper::toDomain);
    }

    /**
     * Finds all sessions for a specific user.
     *
     * @param userId The user ID.
     * @return List of user's sessions.
     */
    @Override
    public List<VotingSession> findByUserId(Long userId) {
        return jpaVotingSessionRepository.findByUserId(userId).stream()
                .map(votingSessionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds valid active sessions for a user.
     *
     * @param userId The user ID.
     * @return List of active sessions.
     */
    @Override
    public List<VotingSession> findActiveByUserId(Long userId) {
        return jpaVotingSessionRepository.findActiveByUserId(userId).stream()
                .map(votingSessionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all active sessions in the system.
     *
     * @return List of active sessions.
     */
    @Override
    public List<VotingSession> findAllActiveSessions() {
        return jpaVotingSessionRepository.findAllActiveSessions().stream()
                .map(votingSessionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds sessions that have expired.
     *
     * @return List of expired sessions.
     */
    @Override
    public List<VotingSession> findExpiredSessions() {
        return jpaVotingSessionRepository.findExpiredSessions(LocalDateTime.now()).stream()
                .map(votingSessionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Invalidates all sessions for a user.
     *
     * @param userId The user ID to invalidate sessions for.
     */
    @Override
    public void invalidateAllUserSessions(Long userId) {
        jpaVotingSessionRepository.invalidateAllUserSessions(userId);
    }

    /**
     * Deletes a session by ID.
     *
     * @param id The session ID.
     */
    @Override
    public void deleteById(Long id) {
        jpaVotingSessionRepository.deleteById(id);
    }

    /**
     * Deletes sessions expired before a specific date.
     *
     * @param beforeDate The expiration threshold.
     * @return The count of deleted sessions.
     */
    @Override
    public Long deleteExpiredSessions(LocalDateTime beforeDate) {
        return jpaVotingSessionRepository.deleteByExpiresAtBefore(beforeDate);
    }

    /**
     * Checks if an active session exists with the given token.
     *
     * @param sessionToken The token to check.
     * @return true if an active session exists.
     */
    @Override
    public boolean existsActiveBySessionToken(String sessionToken) {
        return jpaVotingSessionRepository.existsActiveBySessionToken(sessionToken);
    }

    /**
     * Counts active sessions for a user.
     *
     * @param userId The user ID.
     * @return The count of active sessions.
     */
    @Override
    public Long countActiveByUserId(Long userId) {
        return jpaVotingSessionRepository.countActiveByUserId(userId);
    }
}
