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

    @Override
    public Election save(Election election) {
        log.debug("Guardando elección: {}", election.getTitle());
        ElectionEntity entity = electionEntityMapper.toEntity(election);
        ElectionEntity saved = jpaElectionRepository.save(entity);
        return electionEntityMapper.toDomain(saved);
    }

    @Override
    public Election update(Election election) {
        log.debug("Actualizando elección: {}", election.getId());
        ElectionEntity entity = electionEntityMapper.toEntity(election);
        ElectionEntity updated = jpaElectionRepository.save(entity);
        return electionEntityMapper.toDomain(updated);
    }

    @Override
    public Optional<Election> findById(Long id) {
        return jpaElectionRepository.findById(id)
                .map(electionEntityMapper::toDomain);
    }

    @Override
    public List<Election> findAll() {
        return jpaElectionRepository.findAll().stream()
                .map(electionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Election> findByStatus(ElectionStatus status) {
        return jpaElectionRepository.findByStatus(status).stream()
                .map(electionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Election> findActiveElections() {
        return jpaElectionRepository.findActiveElections(LocalDateTime.now()).stream()
                .map(electionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Election> findScheduledElections() {
        return jpaElectionRepository.findScheduledElections().stream()
                .map(electionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Election> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return jpaElectionRepository.findByDateRange(startDate, endDate).stream()
                .map(electionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Election> findByCreatedBy(Long userId) {
        return jpaElectionRepository.findByCreatedBy(userId).stream()
                .map(electionEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaElectionRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaElectionRepository.existsById(id);
    }

    @Override
    public Long count() {
        return jpaElectionRepository.count();
    }

    @Override
    public Long countByStatus(ElectionStatus status) {
        return jpaElectionRepository.countByStatus(status);
    }
}
