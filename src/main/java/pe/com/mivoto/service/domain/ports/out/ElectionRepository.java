package pe.com.mivoto.service.domain.ports.out;

import pe.com.mivoto.service.domain.enums.ElectionStatus;
import pe.com.mivoto.service.domain.model.Election;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Output port for election data access.
 * Manages persistence and retrieval of election configurations.
 */
public interface ElectionRepository {

    /**
     * Persists a new election.
     *
     * @param election The election to save.
     * @return The saved Election.
     */
    Election save(Election election);

    /**
     * Updates an existing election.
     *
     * @param election The election to update.
     * @return The updated Election.
     */
    Election update(Election election);

    /**
     * Finds an election by its ID.
     *
     * @param id The election ID.
     * @return An Optional containing the Election if found.
     */
    Optional<Election> findById(Long id);

    /**
     * Retrieves all elections.
     *
     * @return A list of all elections.
     */
    List<Election> findAll();

    /**
     * Finds elections by their current status.
     *
     * @param status The election status.
     * @return A list of matching elections.
     */
    List<Election> findByStatus(ElectionStatus status);

    /**
     * Retrieves all active elections.
     *
     * @return A list of active elections.
     */
    List<Election> findActiveElections();

    /**
     * Retrieves all elections scheduled for the future.
     *
     * @return A list of scheduled elections.
     */
    List<Election> findScheduledElections();

    /**
     * Finds elections occurring within a specific date range.
     *
     * @param startDate The start date.
     * @param endDate   The end date.
     * @return A list of elections.
     */
    List<Election> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Finds elections created by a specific user.
     *
     * @param userId The creator's user ID.
     * @return A list of elections.
     */
    List<Election> findByCreatedBy(Long userId);

    /**
     * Deletes an election by its ID.
     *
     * @param id The ID of the election to delete.
     */
    void deleteById(Long id);

    /**
     * Checks if an election exists with the given ID.
     *
     * @param id The election ID.
     * @return true if exists, false otherwise.
     */
    boolean existsById(Long id);

    /**
     * Counts the total number of elections.
     *
     * @return The total count.
     */
    Long count();

    /**
     * Counts the number of elections with a specific status.
     *
     * @param status The election status.
     * @return The count of elections.
     */
    Long countByStatus(ElectionStatus status);
}
