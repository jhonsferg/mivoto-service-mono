package pe.com.mivoto.service.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.Candidate;
import pe.com.mivoto.service.domain.ports.out.CandidateRepository;
import pe.com.mivoto.service.infrastructure.persistence.entities.CandidateEntity;
import pe.com.mivoto.service.infrastructure.persistence.mappers.CandidateEntityMapper;
import pe.com.mivoto.service.infrastructure.persistence.repositories.JpaCandidateRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the Candidate Output Port (CandidateRepository) using the
 * JPA repository.
 * Handles the mapping between the Candidate domain model and the
 * CandidateEntity.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CandidateRepositoryAdapter implements CandidateRepository {

    private final JpaCandidateRepository jpaCandidateRepository;
    private final CandidateEntityMapper candidateEntityMapper;

    @Override
    public Candidate save(Candidate candidate) {
        log.debug("Guardando candidato: {}", candidate.getName());
        CandidateEntity entity = candidateEntityMapper.toEntity(candidate);
        CandidateEntity saved = jpaCandidateRepository.save(entity);
        return candidateEntityMapper.toDomain(saved);
    }

    @Override
    public Candidate update(Candidate candidate) {
        log.debug("Actualizando candidato: {}", candidate.getId());
        CandidateEntity entity = candidateEntityMapper.toEntity(candidate);
        CandidateEntity updated = jpaCandidateRepository.save(entity);
        return candidateEntityMapper.toDomain(updated);
    }

    @Override
    public Optional<Candidate> findById(Long id) {
        return jpaCandidateRepository.findById(id)
                .map(candidateEntityMapper::toDomain);
    }

    @Override
    public List<Candidate> findAll() {
        return jpaCandidateRepository.findAll().stream()
                .map(candidateEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Candidate> findByElectionId(Long electionId) {
        return jpaCandidateRepository.findByElectionId(electionId).stream()
                .map(candidateEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Candidate> findByElectionIdAndNumber(Long electionId, Integer number) {
        return jpaCandidateRepository.findByElectionIdAndNumber(electionId, number)
                .map(candidateEntityMapper::toDomain);
    }

    @Override
    public List<Candidate> findActiveByElectionId(Long electionId) {
        return jpaCandidateRepository.findActiveByElectionId(electionId).stream()
                .map(candidateEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Candidate> findByParty(String party) {
        return jpaCandidateRepository.findByParty(party).stream()
                .map(candidateEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Candidate> findByNameContaining(String name) {
        return jpaCandidateRepository.findByNameContainingIgnoreCase(name).stream()
                .map(candidateEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaCandidateRepository.deleteById(id);
    }

    @Override
    public boolean existsByElectionIdAndNumber(Long electionId, Integer number) {
        return jpaCandidateRepository.existsByElectionIdAndNumber(electionId, number);
    }

    @Override
    public Long countByElectionId(Long electionId) {
        return jpaCandidateRepository.countByElectionId(electionId);
    }

    @Override
    public List<Candidate> findByElectionIdOrderByVoteCountDesc(Long electionId) {
        return jpaCandidateRepository.findByElectionIdOrderByVoteCountDesc(electionId).stream()
                .map(candidateEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}
