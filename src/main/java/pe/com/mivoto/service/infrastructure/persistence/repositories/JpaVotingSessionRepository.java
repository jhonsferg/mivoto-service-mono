package pe.com.mivoto.service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.mivoto.service.infrastructure.persistence.entities.VotingSessionEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaVotingSessionRepository extends JpaRepository<VotingSessionEntity, Long> {
    Optional<VotingSessionEntity> findBySessionToken(String sessionToken);

    Optional<VotingSessionEntity> findByRefreshToken(String refreshToken);

    List<VotingSessionEntity> findByUserId(Long userId);

    @Query("SELECT s FROM VotingSessionEntity s WHERE s.userId = :userId AND s.active = true")
    List<VotingSessionEntity> findActiveByUserId(Long userId);

    @Query("SELECT s FROM VotingSessionEntity s WHERE s.active = true")
    List<VotingSessionEntity> findAllActiveSessions();

    @Query("SELECT s FROM VotingSessionEntity s WHERE s.expiresAt < :now")
    List<VotingSessionEntity> findExpiredSessions(LocalDateTime now);

    @Modifying
    @Query("UPDATE VotingSessionEntity s SET s.active = false WHERE s.userId = :userId")
    void invalidateAllUserSessions(Long userId);

    @Query("DELETE FROM VotingSessionEntity s WHERE s.expiresAt < :beforeDate")
    Long deleteByExpiresAtBefore(LocalDateTime beforeDate);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM VotingSessionEntity s WHERE s.sessionToken = :sessionToken AND s.active = true")
    boolean existsActiveBySessionToken(String sessionToken);

    @Query("SELECT COUNT(s) FROM VotingSessionEntity s WHERE s.userId = :userId AND s.active = true")
    Long countActiveByUserId(Long userId);
}
