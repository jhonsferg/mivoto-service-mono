package pe.com.mivoto.service.domain.ports.out;

import pe.com.mivoto.service.domain.model.Candidate;

import java.util.List;
import java.util.Optional;

/**
 * Output port for candidate data access.
 * Defines methods for persistence, retrieval, and management of candidate
 * records.
 */
public interface CandidateRepository {

    /**
     * Persists or updates a candidate record.
     *
     * @param candidate The candidate to save.
     * @return The saved Candidate.
     */
    Candidate save(Candidate candidate);

    /**
     * Updates an existing candidate record.
     *
     * @param candidate The candidate to update.
     * @return The updated Candidate.
     */
    Candidate update(Candidate candidate);

    /**
     * Finds a candidate by their unique ID.
     *
     * @param id The candidate ID.
     * @return An Optional containing the Candidate if found.
     */
    Optional<Candidate> findById(Long id);

    /**
     * Retrieves all candidates.
     *
     * @return A list of all candidates.
     */
    List<Candidate> findAll();

    /**
     * Finds all candidates associated with a specific election.
     *
     * @param electionId The election ID.
     * @return A list of candidates.
     */
    List<Candidate> findByElectionId(Long electionId);

    /**
     * Finds a candidate by their number within a specific election.
     *
     * @param electionId The election ID.
     * @param number     The candidate number.
     * @return An Optional containing the Candidate if found.
     */
    Optional<Candidate> findByElectionIdAndNumber(Long electionId, Integer number);

    /**
     * Finds all active candidates for a specific election.
     *
     * @param electionId The election ID.
     * @return A list of active candidates.
     */
    List<Candidate> findActiveByElectionId(Long electionId);

    /**
     * Finds candidates belonging to a specific party.
     *
     * @param party The party name.
     * @return A list of candidates.
     */
    List<Candidate> findByParty(String party);

    /**
     * Finds candidates whose names contain the given string.
     *
     * @param name The name substring to search for.
     * @return A list of matching candidates.
     */
    List<Candidate> findByNameContaining(String name);

    /**
     * Deletes a candidate by their ID.
     *
     * @param id The ID of the candidate to delete.
     */
    void deleteById(Long id);

    /**
     * Checks if a candidate with a specific number exists in an election.
     *
     * @param electionId The election ID.
     * @param number     The candidate number.
     * @return true if exists, false otherwise.
     */
    boolean existsByElectionIdAndNumber(Long electionId, Integer number);

    /**
     * Counts the number of candidates in an election.
     *
     * @param electionId The election ID.
     * @return The count of candidates.
     */
    Long countByElectionId(Long electionId);

    /**
     * Retrieves candidates for an election ordered by their vote count
     * (descending).
     *
     * @param electionId The election ID.
     * @return A sorted list of candidates.
     */
    List<Candidate> findByElectionIdOrderByVoteCountDesc(Long electionId);
}
