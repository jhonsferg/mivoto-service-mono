package pe.com.mivoto.service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.mivoto.service.domain.enums.ElectionStatus;
import pe.com.mivoto.service.infrastructure.persistence.entities.ElectionEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JpaElectionRepository extends JpaRepository<ElectionEntity, Long> {
    List<ElectionEntity> findByStatus(ElectionStatus status);

    @Query("SELECT e FROM ElectionEntity e WHERE e.status = 'ACTIVE' AND e.startDate <= :now AND e.endDate >= :now")
    List<ElectionEntity> findActiveElections(LocalDateTime now);

    @Query("SELECT e FROM ElectionEntity e WHERE e.status = 'SCHEDULED'")
    List<ElectionEntity> findScheduledElections();

    @Query("SELECT e FROM ElectionEntity e WHERE e.startDate >= :startDate AND e.endDate <= :endDate")
    List<ElectionEntity> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<ElectionEntity> findByCreatedBy(Long userId);

    Long countByStatus(ElectionStatus status);
}
