package pe.com.mivoto.service.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * JPA Entity for Candidates.
 * Maps to the "candidates" table.
 * Represents a candidate running in an election.
 */
@Entity
@Table(name = "candidates", indexes = {
        @Index(name = "idx_candidate_election_id", columnList = "election_id"),
        @Index(name = "idx_candidate_election_number", columnList = "election_id, number")
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"election_id", "number"})
})
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id", nullable = false)
    private ElectionEntity election;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "party", length = 150)
    private String party;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "photo_url", length = 500)
    private String photoUrl;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "vote_count", nullable = false)
    @Builder.Default
    private Integer voteCount = 0;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
