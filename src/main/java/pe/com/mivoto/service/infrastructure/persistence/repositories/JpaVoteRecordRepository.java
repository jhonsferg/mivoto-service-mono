package pe.com.mivoto.service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.mivoto.service.infrastructure.persistence.entities.VoteRecordEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaVoteRecordRepository extends JpaRepository<VoteRecordEntity, Long> {
    Optional<VoteRecordEntity> findByVoteHash(String voteHash);

    List<VoteRecordEntity> findByUserId(Long userId);

    List<VoteRecordEntity> findByElectionId(Long electionId);

    List<VoteRecordEntity> findByVoteId(Long voteId);
}
