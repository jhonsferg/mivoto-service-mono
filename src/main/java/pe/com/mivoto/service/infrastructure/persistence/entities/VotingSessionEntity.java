package pe.com.mivoto.service.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * JPA Entity for Voting Sessions.
 * Maps to the "voting_sessions" table.
 * Manages user session tokens and status.
 */
@Entity
@Table(name = "voting_sessions", indexes = {
        @Index(name = "idx_session_user_id", columnList = "user_id"),
        @Index(name = "idx_session_token", columnList = "session_token"),
        @Index(name = "idx_refresh_token", columnList = "refresh_token")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VotingSessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "session_token", unique = true, nullable = false, length = 500)
    private String sessionToken;

    @Column(name = "refresh_token", unique = true, nullable = false, length = 500)
    private String refreshToken;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "last_accessed_at")
    private LocalDateTime lastAccessedAt;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "active", nullable = false)
    private Boolean active;
}
