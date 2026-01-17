package pe.com.mivoto.service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.mivoto.service.infrastructure.persistence.entities.CandidateEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaCandidateRepository extends JpaRepository<CandidateEntity, Long> {
    @Query("SELECT c FROM CandidateEntity c WHERE c.election.id = :electionId")
    List<CandidateEntity> findByElectionId(Long electionId);

    @Query("SELECT c FROM CandidateEntity c WHERE c.election.id = :electionId AND c.number = :number")
    Optional<CandidateEntity> findByElectionIdAndNumber(Long electionId, Integer number);

    @Query("SELECT c FROM CandidateEntity c WHERE c.election.id = :electionId AND c.active = true")
    List<CandidateEntity> findActiveByElectionId(Long electionId);

    List<CandidateEntity> findByParty(String party);

    List<CandidateEntity> findByNameContainingIgnoreCase(String name);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CandidateEntity c WHERE c.election.id = :electionId AND c.number = :number")
    boolean existsByElectionIdAndNumber(Long electionId, Integer number);

    @Query("SELECT COUNT(c) FROM CandidateEntity c WHERE c.election.id = :electionId")
    Long countByElectionId(Long electionId);

    @Query("SELECT c FROM CandidateEntity c WHERE c.election.id = :electionId ORDER BY c.voteCount DESC")
    List<CandidateEntity> findByElectionIdOrderByVoteCountDesc(Long electionId);
}
