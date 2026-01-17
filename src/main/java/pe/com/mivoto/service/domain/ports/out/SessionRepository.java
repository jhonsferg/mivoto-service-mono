package pe.com.mivoto.service.domain.ports.out;

import pe.com.mivoto.service.domain.model.VotingSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SessionRepository {
    VotingSession save(VotingSession session);

    VotingSession update(VotingSession session);

    Optional<VotingSession> findById(Long id);

    Optional<VotingSession> findBySessionToken(String sessionToken);

    Optional<VotingSession> findByRefreshToken(String refreshToken);

    List<VotingSession> findByUserId(Long userId);

    List<VotingSession> findActiveByUserId(Long userId);

    List<VotingSession> findAllActiveSessions();

    List<VotingSession> findExpiredSessions();

    void invalidateAllUserSessions(Long userId);

    void deleteById(Long id);

    Long deleteExpiredSessions(LocalDateTime beforeDate);

    boolean existsActiveBySessionToken(String sessionToken);

    Long countActiveByUserId(Long userId);
}
