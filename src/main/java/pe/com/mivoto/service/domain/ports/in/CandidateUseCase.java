package pe.com.mivoto.service.domain.ports.in;

import pe.com.mivoto.service.domain.model.Candidate;

import java.util.List;

/**
 * Input port for candidate management operations.
 * Allows registering, updating, and retrieving candidate information.
 */
public interface CandidateUseCase {

    /**
     * Registers a new candidate for an election.
     *
     * @param candidate The candidate data.
     * @return The registered Candidate with generated ID.
     */
    Candidate registerCandidate(Candidate candidate);

    /**
     * Updates an existing candidate's information.
     *
     * @param candidateId The ID of the candidate to update.
     * @param candidate   The new candidate data.
     * @return The updated Candidate.
     */
    Candidate updateCandidate(Long candidateId, Candidate candidate);

    /**
     * Retrieves a candidate by their unique ID.
     *
     * @param candidateId The candidate ID.
     * @return The Candidate object.
     */
    Candidate getCandidateById(Long candidateId);

    /**
     * Retrieves all candidates registered for a specific election.
     *
     * @param electionId The election ID.
     * @return A list of candidates.
     */
    List<Candidate> getCandidatesByElection(Long electionId);

    /**
     * Finds a candidate by their assigned number within an election.
     *
     * @param electionId The election ID.
     * @param number     The candidate number on the ballot.
     * @return The matching Candidate.
     */
    Candidate findCandidateByNumber(Long electionId, Integer number);

    /**
     * Activates a candidate, enabling them to receive votes.
     *
     * @param candidateId The candidate ID.
     */
    void activateCandidate(Long candidateId);

    /**
     * Deactivates a candidate, preventing them from receiving votes.
     *
     * @param candidateId The candidate ID.
     */
    void deactivateCandidate(Long candidateId);

    /**
     * Permanently deletes a candidate from the system.
     *
     * @param candidateId The candidate ID.
     */
    void deleteCandidate(Long candidateId);

    /**
     * Retrieves all currently active candidates for a specific election.
     *
     * @param electionId The election ID.
     * @return A list of active candidates.
     */
    List<Candidate> getActiveCandidates(Long electionId);

    /**
     * Finds candidates belonging to a specific party across all elections.
     *
     * @param party The party name.
     * @return A list of matching candidates.
     */
    List<Candidate> findCandidatesByParty(String party);
}
