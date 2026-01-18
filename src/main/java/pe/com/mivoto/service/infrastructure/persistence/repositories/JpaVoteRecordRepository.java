package pe.com.mivoto.service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.mivoto.service.infrastructure.persistence.entities.VoteRecordEntity;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for Vote Records.
 * Provides database access methods for managing VoteRecordEntity.
 */
@Repository
public interface JpaVoteRecordRepository extends JpaRepository<VoteRecordEntity, Long> {

    /**
     * Finds a vote record by its vote hash.
     *
     * @param voteHash The vote hash.
     * @return Optional containing the vote record if found.
     */
    Optional<VoteRecordEntity> findByVoteHash(String voteHash);

    /**
     * Finds vote records by user ID.
     *
     * @param userId The user ID.
     * @return List of vote records.
     */
    List<VoteRecordEntity> findByUserId(Long userId);

    /**
     * Finds vote records by election ID.
     *
     * @param electionId The election ID.
     * @return List of vote records.
     */
    List<VoteRecordEntity> findByElectionId(Long electionId);

    /**
     * Finds vote records by original vote ID.
     *
     * @param voteId The original vote ID.
     * @return List of vote records.
     */
    List<VoteRecordEntity> findByVoteId(Long voteId);
}
