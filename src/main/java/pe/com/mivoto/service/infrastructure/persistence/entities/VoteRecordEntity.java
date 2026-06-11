package pe.com.mivoto.service.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * JPA Entity for Vote Records.
 * Maps to the "vote_records" table.
 * Stores archival and audit data for votes.
 */
@Entity
@Table(name = "vote_records", indexes = {
        @Index(name = "idx_record_vote_id", columnList = "vote_id"),
        @Index(name = "idx_record_user_id", columnList = "user_id"),
        @Index(name = "idx_record_election_id", columnList = "election_id"),
        @Index(name = "idx_record_vote_hash", columnList = "vote_hash")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vote_id", nullable = false)
    private Long voteId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "election_id", nullable = false)
    private Long electionId;

    @Column(name = "vote_hash", nullable = false, length = 64)
    private String voteHash;

    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @Column(name = "verified", nullable = false)
    private Boolean verified;

    @Column(name = "blockchain_hash", length = 128)
    private String blockchainHash;
}
