package pe.com.mivoto.service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.mivoto.service.infrastructure.persistence.entities.CandidateEntity;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for Candidates.
 * Provides database access methods for managing CandidateEntity.
 */
@Repository
public interface JpaCandidateRepository extends JpaRepository<CandidateEntity, Long> {

    /**
     * Finds candidates by election ID.
     *
     * @param electionId The election ID.
     * @return List of candidates.
     */
    @Query("SELECT c FROM CandidateEntity c WHERE c.election.id = :electionId")
    List<CandidateEntity> findByElectionId(Long electionId);

    /**
     * Finds a candidate by election ID and candidate number.
     *
     * @param electionId The election ID.
     * @param number     The candidate number.
     * @return Optional containing the candidate if found.
     */
    @Query("SELECT c FROM CandidateEntity c WHERE c.election.id = :electionId AND c.number = :number")
    Optional<CandidateEntity> findByElectionIdAndNumber(Long electionId, Integer number);

    /**
     * Finds active candidates for a specific election.
     *
     * @param electionId The election ID.
     * @return List of active candidates.
     */
    @Query("SELECT c FROM CandidateEntity c WHERE c.election.id = :electionId AND c.active = true")
    List<CandidateEntity> findActiveByElectionId(Long electionId);

    /**
     * Finds candidates by party name.
     *
     * @param party The party name.
     * @return List of candidates.
     */
    List<CandidateEntity> findByParty(String party);

    /**
     * Finds candidates by name containing search text (case insensitive).
     *
     * @param name The name to search for.
     * @return List of candidates.
     */
    List<CandidateEntity> findByNameContainingIgnoreCase(String name);

    /**
     * Checks if a candidate with the given number exists in an election.
     *
     * @param electionId The election ID.
     * @param number     The candidate number.
     * @return true if exists, false otherwise.
     */
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CandidateEntity c WHERE c.election.id = :electionId AND c.number = :number")
    boolean existsByElectionIdAndNumber(Long electionId, Integer number);

    /**
     * Counts candidates for a specific election.
     *
     * @param electionId The election ID.
     * @return The count of candidates.
     */
    @Query("SELECT COUNT(c) FROM CandidateEntity c WHERE c.election.id = :electionId")
    Long countByElectionId(Long electionId);

    /**
     * Finds candidates for an election ordered by vote count descending.
     *
     * @param electionId The election ID.
     * @return List of candidates ordered by votes.
     */
    @Query("SELECT c FROM CandidateEntity c WHERE c.election.id = :electionId ORDER BY c.voteCount DESC")
    List<CandidateEntity> findByElectionIdOrderByVoteCountDesc(Long electionId);
}
