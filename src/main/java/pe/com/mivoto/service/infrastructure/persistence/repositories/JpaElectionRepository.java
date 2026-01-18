package pe.com.mivoto.service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.mivoto.service.domain.enums.ElectionStatus;
import pe.com.mivoto.service.infrastructure.persistence.entities.ElectionEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * JPA Repository for Elections.
 * Provides database access methods for managing ElectionEntity.
 */
@Repository
public interface JpaElectionRepository extends JpaRepository<ElectionEntity, Long> {

    /**
     * Finds elections by status.
     *
     * @param status The election status.
     * @return List of elections.
     */
    List<ElectionEntity> findByStatus(ElectionStatus status);

    /**
     * Finds active elections based on status and current date.
     *
     * @param now The current date and time.
     * @return List of active elections.
     */
    @Query("SELECT e FROM ElectionEntity e WHERE e.status = 'ACTIVE' AND e.startDate <= :now AND e.endDate >= :now")
    List<ElectionEntity> findActiveElections(LocalDateTime now);

    /**
     * Finds elections scheduled for the future.
     *
     * @return List of scheduled elections.
     */
    @Query("SELECT e FROM ElectionEntity e WHERE e.status = 'SCHEDULED'")
    List<ElectionEntity> findScheduledElections();

    /**
     * Finds elections within a specific date range.
     *
     * @param startDate The start date.
     * @param endDate   The end date.
     * @return List of elections.
     */
    @Query("SELECT e FROM ElectionEntity e WHERE e.startDate >= :startDate AND e.endDate <= :endDate")
    List<ElectionEntity> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Finds elections created by a specific user.
     *
     * @param userId The creator's user ID.
     * @return List of elections.
     */
    List<ElectionEntity> findByCreatedBy(Long userId);

    /**
     * Counts elections by status.
     *
     * @param status The election status.
     * @return The count of elections.
     */
    Long countByStatus(ElectionStatus status);
}
