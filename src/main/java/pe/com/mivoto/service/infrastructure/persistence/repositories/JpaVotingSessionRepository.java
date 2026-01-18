package pe.com.mivoto.service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.mivoto.service.infrastructure.persistence.entities.VotingSessionEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for Voting Sessions.
 * Provides database access methods for managing VotingSessionEntity.
 */
@Repository
public interface JpaVotingSessionRepository extends JpaRepository<VotingSessionEntity, Long> {

    /**
     * Finds a session by its token.
     *
     * @param sessionToken The session token.
     * @return Optional containing the session if found.
     */
    Optional<VotingSessionEntity> findBySessionToken(String sessionToken);

    /**
     * Finds a session by its refresh token.
     *
     * @param refreshToken The refresh token.
     * @return Optional containing the session if found.
     */
    Optional<VotingSessionEntity> findByRefreshToken(String refreshToken);

    /**
     * Finds all sessions for a user.
     *
     * @param userId The user ID.
     * @return List of sessions.
     */
    List<VotingSessionEntity> findByUserId(Long userId);

    /**
     * Finds active sessions for a user.
     *
     * @param userId The user ID.
     * @return List of active sessions.
     */
    @Query("SELECT s FROM VotingSessionEntity s WHERE s.userId = :userId AND s.active = true")
    List<VotingSessionEntity> findActiveByUserId(Long userId);

    /**
     * Finds all active sessions in the system.
     *
     * @return List of active sessions.
     */
    @Query("SELECT s FROM VotingSessionEntity s WHERE s.active = true")
    List<VotingSessionEntity> findAllActiveSessions();

    /**
     * Finds expired sessions.
     *
     * @param now The current date and time.
     * @return List of expired sessions.
     */
    @Query("SELECT s FROM VotingSessionEntity s WHERE s.expiresAt < :now")
    List<VotingSessionEntity> findExpiredSessions(LocalDateTime now);

    /**
     * Invalidates all sessions for a user.
     *
     * @param userId The user ID.
     */
    @Modifying
    @Query("UPDATE VotingSessionEntity s SET s.active = false WHERE s.userId = :userId")
    void invalidateAllUserSessions(Long userId);

    /**
     * Deletes sessions expired before a specific date.
     *
     * @param beforeDate The threshold date.
     * @return The number of deleted sessions.
     */
    @Query("DELETE FROM VotingSessionEntity s WHERE s.expiresAt < :beforeDate")
    Long deleteByExpiresAtBefore(LocalDateTime beforeDate);

    /**
     * Checks if an active session with the given token exists.
     *
     * @param sessionToken The session token.
     * @return true if exists and active, false otherwise.
     */
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM VotingSessionEntity s WHERE s.sessionToken = :sessionToken AND s.active = true")
    boolean existsActiveBySessionToken(String sessionToken);

    /**
     * Counts active sessions for a user.
     *
     * @param userId The user ID.
     * @return The count of active sessions.
     */
    @Query("SELECT COUNT(s) FROM VotingSessionEntity s WHERE s.userId = :userId AND s.active = true")
    Long countActiveByUserId(Long userId);
}
