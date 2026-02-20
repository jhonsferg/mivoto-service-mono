package pe.com.mivoto.service.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.enums.ElectionStatus;
import pe.com.mivoto.service.domain.model.Election;
import pe.com.mivoto.service.domain.ports.out.ElectionRepository;
import pe.com.mivoto.service.infrastructure.persistence.entities.ElectionEntity;
import pe.com.mivoto.service.infrastructure.persistence.mappers.ElectionEntityMapper;
import pe.com.mivoto.service.infrastructure.persistence.repositories.JpaElectionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the Election Output Port (ElectionRepository) using the JPA
 * repository.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ElectionRepositoryAdapter implements ElectionRepository {

    private final JpaElectionRepository jpaElectionRepository;
    private final ElectionEntityMapper electionEntityMapper;

    /**
     * Saves a new election.
     *
     * @param election The election to save.
     * @return The saved election.
     */
    @Override
    public Election save(Election election) {
        log.debug("Guardando elección: {}", election.getTitle());
        ElectionEntity entity = electionEntityMapper.toEntity(election);
        ElectionEntity saved = jpaElectionRepository.save(entity);
        return electionEntityMapper.toDomain(saved);
    }

    /**
     * Updates an existing election.
     *
     * @param election The election updates.
     * @return The updated election.
     */
    @Override
    public Election update(Election election) {
        log.debug("Actualizando elección: {}", election.getId());
        ElectionEntity entity = electionEntityMapper.toEntity(election);
        ElectionEntity updated = jpaElectionRepository.save(entity);
        return electionEntityMapper.toDomain(updated);
    }

    /**
     * Finds an election by ID.
     *
     * @param id The election ID.
     * @return Optional containing the election if found.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Optional<Election> findById(Long id) {
        return jpaElectionRepository.findById(id)
                .map(electionEntityMapper::toDomain);
    }

    /**
     * Retrieves all elections.
     *
     * @return List of all elections.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Election> findAll() {
        return jpaElectionRepository.findAll().stream()
                .map(electionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds elections by status.
     *
     * @param status The election status.
     * @return List of matching elections.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Election> findByStatus(ElectionStatus status) {
        return jpaElectionRepository.findByStatus(status).stream()
                .map(electionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds all currently active elections.
     *
     * @return List of active elections.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Election> findActiveElections() {
        return jpaElectionRepository.findActiveElections(LocalDateTime.now()).stream()
                .map(electionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds all scheduled elections.
     *
     * @return List of scheduled elections.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Election> findScheduledElections() {
        return jpaElectionRepository.findScheduledElections().stream()
                .map(electionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds elections within a date range.
     *
     * @param startDate The start date.
     * @param endDate   The end date.
     * @return List of elections in the range.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Election> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return jpaElectionRepository.findByDateRange(startDate, endDate).stream()
                .map(electionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds elections created by a specific user.
     *
     * @param userId The user ID.
     * @return List of elections.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Election> findByCreatedBy(Long userId) {
        return jpaElectionRepository.findByCreatedBy(userId).stream()
                .map(electionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Deletes an election by ID.
     *
     * @param id The election ID.
     */
    @Override
    public void deleteById(Long id) {
        jpaElectionRepository.deleteById(id);
    }

    /**
     * Checks if an election exists by ID.
     *
     * @param id The election ID.
     * @return true if exists.
     */
    @Override
    public boolean existsById(Long id) {
        return jpaElectionRepository.existsById(id);
    }

    /**
     * Counts the total number of elections.
     *
     * @return The count.
     */
    @Override
    public Long count() {
        return jpaElectionRepository.count();
    }

    /**
     * Counts elections by status.
     *
     * @param status The election status.
     * @return The count.
     */
    @Override
    public Long countByStatus(ElectionStatus status) {
        return jpaElectionRepository.countByStatus(status);
    }
}
