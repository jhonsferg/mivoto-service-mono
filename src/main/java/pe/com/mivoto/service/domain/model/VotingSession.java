package pe.com.mivoto.service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents an active authentication session for a user.
 * Manages access and refresh tokens, expiration, and activity tracking.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VotingSession {

    /**
     * Unique identifier for the session.
     */
    private Long id;

    /**
     * ID of the user owner of the session.
     */
    private Long userId;

    /**
     * JWT access token for the current session.
     */
    private String sessionToken;

    /**
     * Token used to obtain a new access token after expiration.
     */
    private String refreshToken;

    /**
     * Timestamp when the session was created.
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp when the session officially expires.
     */
    private LocalDateTime expiresAt;

    /**
     * Timestamp of the last time the session was used for an operation.
     */
    private LocalDateTime lastAccessedAt;

    /**
     * IP address from which the session was established.
     */
    private String ipAddress;

    /**
     * User agent of the client that established the session.
     */
    private String userAgent;

    /**
     * Indicates if the session is currently active.
     */
    private Boolean active;

    /**
     * Checks if the session has passed its expiration time.
     *
     * @return true if expired, false otherwise.
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    /**
     * Checks if the session is both active and not expired.
     *
     * @return true if valid.
     */
    public boolean isValid() {
        return active && !isExpired();
    }

    /**
     * Updates the last accessed timestamp to the current time.
     */
    public void updateAccess() {
        this.lastAccessedAt = LocalDateTime.now();
    }

    /**
     * Invalidates the session, preventing further use even if not expired.
     */
    public void invalidate() {
        this.active = false;
    }

    /**
     * Determines if the session is close to expiration and should be refreshed.
     *
     * @return true if expiring within the next 5 minutes.
     */
    public boolean needsRefresh() {
        return LocalDateTime.now().plusMinutes(5).isAfter(expiresAt);
    }

    /**
     * Calculates the total duration of the session in minutes since creation.
     *
     * @return The duration in minutes.
     */
    public long getSessionDurationMinutes() {
        return java.time.Duration.between(createdAt, LocalDateTime.now()).toMinutes();
    }
}
