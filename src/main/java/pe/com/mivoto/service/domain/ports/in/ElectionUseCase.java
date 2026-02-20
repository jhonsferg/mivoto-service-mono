package pe.com.mivoto.service.domain.ports.in;

import pe.com.mivoto.service.domain.model.Candidate;
import pe.com.mivoto.service.domain.model.Election;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Input port for election management and results operations.
 * Manages the full lifecycle of an election and providing statistical data.
 */
public interface ElectionUseCase {
    /**
     * Creates a new election record.
     *
     * @param election  The election configuration.
     * @param createdBy ID of the user creating the election.
     * @return The created Election.
     */
    Election createElection(Election election, Long createdBy);

    /**
     * Updates an existing election's configuration.
     *
     * @param electionId The election ID.
     * @param election   The new configuration data.
     * @return The updated Election.
     */
    Election updateElection(Long electionId, Election election);

    /**
     * Retrieves an election by its ID.
     *
     * @param electionId The election ID.
     * @return The Election object.
     */
    Election getElectionById(Long electionId);

    /**
     * Retrieves all elections.
     *
     * @return List of all elections.
     */
    List<Election> getAllElections();

    /**
     * Retrieves all elections currently in ACTIVE status.
     *
     * @return List of active elections.
     */
    List<Election> getActiveElections();

    /**
     * Retrieves elections filtered by their lifecycle status.
     *
     * @param status The status to filter by.
     * @return List of matching elections.
     */
    List<Election> getElectionsByStatus(pe.com.mivoto.service.domain.enums.ElectionStatus status);

    /**
     * Schedules a draft election for a future date.
     *
     * @param electionId The election ID.
     */
    void scheduleElection(Long electionId);

    /**
     * Officially starts a scheduled election.
     *
     * @param electionId The election ID.
     */
    void startElection(Long electionId);

    /**
     * Formally closes an active election to stop further voting.
     *
     * @param electionId The election ID.
     */
    void closeElection(Long electionId);

    /**
     * Cancels an election and records the reason.
     *
     * @param electionId The election ID.
     * @param reason     Descriptive reason for cancellation.
     */
    void cancelElection(Long electionId, String reason);

    /**
     * Calculates the current vote counts for each candidate in an election.
     *
     * @param electionId The election ID.
     * @return A map of candidates and their corresponding vote counts.
     */
    Map<Candidate, Long> getElectionResults(Long electionId);

    /**
     * Retrives detailed statistical information about an election.
     *
     * @param electionId The election ID.
     * @return aggregated election statistics.
     */
    ElectionStatistics getElectionStatistics(Long electionId);

    /**
     * Searches for elections scheduled within a specific date range.
     *
     * @param startDate Start of the search range.
     * @param endDate   End of the search range.
     * @return List of matching elections.
     */
    List<Election> findElectionsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Record containing aggregated statistical data for an election.
     *
     * @param totalVotes        Total votes cast in the election.
     * @param totalValidVotes   Number of votes that passed integrity checks.
     * @param totalInvalidVotes Number of votes rejected or failed integrity checks.
     * @param participationRate Percentage of registered voters who participated.
     * @param leadingCandidate  The candidate currently in the lead.
     */
    record ElectionStatistics(
            Long totalVotes,
            Long totalValidVotes,
            Long totalInvalidVotes,
            Double participationRate,
            Candidate leadingCandidate) {
    }
}
