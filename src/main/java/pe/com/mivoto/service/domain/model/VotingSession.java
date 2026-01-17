package pe.com.mivoto.service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VotingSession {

    private Long id;
    private Long userId;
    private String sessionToken;
    private String refreshToken;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime lastAccessedAt;
    private String ipAddress;
    private String userAgent;
    private Boolean active;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean isValid() {
        return active && !isExpired();
    }

    public void updateAccess() {
        this.lastAccessedAt = LocalDateTime.now();
    }

    public void invalidate() {
        this.active = false;
    }

    public boolean needsRefresh() {
        return LocalDateTime.now().plusMinutes(5).isAfter(expiresAt);
    }

    public long getSessionDurationMinutes() {
        return java.time.Duration.between(createdAt, LocalDateTime.now()).toMinutes();
    }
}
