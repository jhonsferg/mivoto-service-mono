package pe.com.mivoto.service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.mivoto.service.domain.enums.VoteStatus;
import pe.com.mivoto.service.infrastructure.persistence.entities.VoteEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaVoteRepository extends JpaRepository<VoteEntity, Long> {
    Optional<VoteEntity> findByVoteHash(String voteHash);

    List<VoteEntity> findByUserId(Long userId);

    List<VoteEntity> findByElectionId(Long electionId);

    List<VoteEntity> findByCandidateId(Long candidateId);

    boolean existsByUserIdAndElectionId(Long userId, Long electionId);

    Optional<VoteEntity> findByUserIdAndElectionId(Long userId, Long electionId);

    @Query("SELECT COUNT(v) FROM VoteEntity v WHERE v.electionId = :electionId")
    Long countByElectionId(Long electionId);

    @Query("SELECT COUNT(v) FROM VoteEntity v WHERE v.candidateId = :candidateId")
    Long countByCandidateId(Long candidateId);

    List<VoteEntity> findByStatus(VoteStatus status);

    List<VoteEntity> findByVotedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
