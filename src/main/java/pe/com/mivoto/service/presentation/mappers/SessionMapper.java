package pe.com.mivoto.service.presentation.mappers;

import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.VotingSession;
import pe.com.mivoto.service.infrastructure.persistence.entities.VotingSessionEntity;

@Component
public class SessionMapper {
    public VotingSession toDomain(VotingSessionEntity entity) {
        if (entity == null) {
            return null;
        }

        return VotingSession.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .sessionToken(entity.getSessionToken())
                .refreshToken(entity.getRefreshToken())
                .createdAt(entity.getCreatedAt())
                .expiresAt(entity.getExpiresAt())
                .lastAccessedAt(entity.getLastAccessedAt())
                .ipAddress(entity.getIpAddress())
                .userAgent(entity.getUserAgent())
                .active(entity.getActive())
                .build();
    }

    public VotingSessionEntity toEntity(VotingSession domain) {
        if (domain == null) {
            return null;
        }

        return VotingSessionEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .sessionToken(domain.getSessionToken())
                .refreshToken(domain.getRefreshToken())
                .createdAt(domain.getCreatedAt())
                .expiresAt(domain.getExpiresAt())
                .lastAccessedAt(domain.getLastAccessedAt())
                .ipAddress(domain.getIpAddress())
                .userAgent(domain.getUserAgent())
                .active(domain.getActive())
                .build();
    }
}
