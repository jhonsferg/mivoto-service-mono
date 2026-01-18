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

    @Override
    public VotingSession save(VotingSession session) {
        log.debug("Guardando sesión para el usuario: {}", session.getUserId());
        VotingSessionEntity entity = votingSessionEntityMapper.toEntity(session);
        VotingSessionEntity saved = jpaVotingSessionRepository.save(entity);
        return votingSessionEntityMapper.toDomain(saved);
    }

    @Override
    public VotingSession update(VotingSession session) {
        log.debug("Actualizando sesión: {}", session.getId());
        VotingSessionEntity entity = votingSessionEntityMapper.toEntity(session);
        VotingSessionEntity updated = jpaVotingSessionRepository.save(entity);
        return votingSessionEntityMapper.toDomain(updated);
    }

    @Override
    public Optional<VotingSession> findById(Long id) {
        return jpaVotingSessionRepository.findById(id)
                .map(votingSessionEntityMapper::toDomain);
    }

    @Override
    public Optional<VotingSession> findBySessionToken(String sessionToken) {
        return jpaVotingSessionRepository.findBySessionToken(sessionToken)
                .map(votingSessionEntityMapper::toDomain);
    }

    @Override
    public Optional<VotingSession> findByRefreshToken(String refreshToken) {
        return jpaVotingSessionRepository.findByRefreshToken(refreshToken)
                .map(votingSessionEntityMapper::toDomain);
    }

    @Override
    public List<VotingSession> findByUserId(Long userId) {
        return jpaVotingSessionRepository.findByUserId(userId).stream()
                .map(votingSessionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<VotingSession> findActiveByUserId(Long userId) {
        return jpaVotingSessionRepository.findActiveByUserId(userId).stream()
                .map(votingSessionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<VotingSession> findAllActiveSessions() {
        return jpaVotingSessionRepository.findAllActiveSessions().stream()
                .map(votingSessionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<VotingSession> findExpiredSessions() {
        return jpaVotingSessionRepository.findExpiredSessions(LocalDateTime.now()).stream()
                .map(votingSessionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void invalidateAllUserSessions(Long userId) {
        jpaVotingSessionRepository.invalidateAllUserSessions(userId);
    }

    @Override
    public void deleteById(Long id) {
        jpaVotingSessionRepository.deleteById(id);
    }

    @Override
    public Long deleteExpiredSessions(LocalDateTime beforeDate) {
        return jpaVotingSessionRepository.deleteByExpiresAtBefore(beforeDate);
    }

    @Override
    public boolean existsActiveBySessionToken(String sessionToken) {
        return jpaVotingSessionRepository.existsActiveBySessionToken(sessionToken);
    }

    @Override
    public Long countActiveByUserId(Long userId) {
        return jpaVotingSessionRepository.countActiveByUserId(userId);
    }
}
