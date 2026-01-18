package pe.com.mivoto.service.domain.ports.out;

import pe.com.mivoto.service.domain.model.VotingSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Output port for session management data access.
 * Handles persistence and retrieval of voting sessions.
 */
public interface SessionRepository {

    /**
     * Persists a new voting session.
     *
     * @param session The session to save.
     * @return The saved VotingSession.
     */
    VotingSession save(VotingSession session);

    /**
     * Updates an existing session.
     *
     * @param session The session to update.
     * @return The updated VotingSession.
     */
    VotingSession update(VotingSession session);

    /**
     * Finds a session by its ID.
     *
     * @param id The session ID.
     * @return An Optional containing the VotingSession if found.
     */
    Optional<VotingSession> findById(Long id);

    /**
     * Finds a session by its unique session token.
     *
     * @param sessionToken The session token.
     * @return An Optional containing the VotingSession if found.
     */
    Optional<VotingSession> findBySessionToken(String sessionToken);

    /**
     * Finds a session by its refresh token.
     *
     * @param refreshToken The refresh token.
     * @return An Optional containing the VotingSession if found.
     */
    Optional<VotingSession> findByRefreshToken(String refreshToken);

    /**
     * Retrieves all sessions for a specific user.
     *
     * @param userId The user ID.
     * @return A list of sessions.
     */
    List<VotingSession> findByUserId(Long userId);

    /**
     * Retrieves all active sessions for a specific user.
     *
     * @param userId The user ID.
     * @return A list of active sessions.
     */
    List<VotingSession> findActiveByUserId(Long userId);

    /**
     * Retrieves all currently active sessions across the system.
     *
     * @return A list of active sessions.
     */
    List<VotingSession> findAllActiveSessions();

    /**
     * Retrieves sessions that have expired.
     *
     * @return A list of expired sessions.
     */
    List<VotingSession> findExpiredSessions();

    /**
     * Invalidates all active sessions for a specific user (e.g., logout all
     * devices).
     *
     * @param userId The user ID.
     */
    void invalidateAllUserSessions(Long userId);

    /**
     * Deletes a session by its ID.
     *
     * @param id The session ID.
     */
    void deleteById(Long id);

    /**
     * Deletes all sessions that expired before a specific date.
     *
     * @param beforeDate The cutoff date.
     * @return The number of deleted sessions.
     */
    Long deleteExpiredSessions(LocalDateTime beforeDate);

    /**
     * Checks if an active session exists with the given token.
     *
     * @param sessionToken The session token.
     * @return true if an active session exists, false otherwise.
     */
    boolean existsActiveBySessionToken(String sessionToken);

    /**
     * Counts the number of active sessions for a user.
     *
     * @param userId The user ID.
     * @return The count of active sessions.
     */
    Long countActiveByUserId(Long userId);
}
