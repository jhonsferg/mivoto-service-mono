package pe.com.mivoto.service.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.mivoto.service.domain.enums.VoteStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "votes",
        indexes = {
                @Index(name = "idx_user_election", columnList = "user_id, election_id"),
                @Index(name = "idx_election_id", columnList = "election_id"),
                @Index(name = "idx_vote_hash", columnList = "vote_hash")
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "election_id"})
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "election_id", nullable = false)
    private Long electionId;

    @Column(name = "candidate_id", nullable = false)
    private Long candidateId;

    @Column(name = "vote_hash", unique = true, nullable = false, length = 64)
    private String voteHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private VoteStatus status;

    @Column(name = "verified", nullable = false)
    private Boolean verified;

    @Column(name = "verification_code", length = 50)
    private String verificationCode;

    @Column(name = "voted_at", nullable = false)
    private LocalDateTime votedAt;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", length = 500)
    private String userAgent;
}
