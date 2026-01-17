package pe.com.mivoto.service.domain.ports.out;

import pe.com.mivoto.service.domain.enums.ElectionStatus;
import pe.com.mivoto.service.domain.model.Election;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ElectionRepository {
    Election save(Election election);

    Election update(Election election);

    Optional<Election> findById(Long id);

    List<Election> findAll();

    List<Election> findByStatus(ElectionStatus status);

    List<Election> findActiveElections();

    List<Election> findScheduledElections();

    List<Election> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<Election> findByCreatedBy(Long userId);

    void deleteById(Long id);

    boolean existsById(Long id);

    Long count();

    Long countByStatus(ElectionStatus status);
}
