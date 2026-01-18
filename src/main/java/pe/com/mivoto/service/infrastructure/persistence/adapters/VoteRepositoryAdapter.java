package pe.com.mivoto.service.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.enums.VoteStatus;
import pe.com.mivoto.service.domain.model.Vote;
import pe.com.mivoto.service.domain.model.VoteRecord;
import pe.com.mivoto.service.domain.ports.out.VoteRepository;
import pe.com.mivoto.service.infrastructure.persistence.entities.VoteEntity;
import pe.com.mivoto.service.infrastructure.persistence.entities.VoteRecordEntity;
import pe.com.mivoto.service.infrastructure.persistence.mappers.VoteEntityMapper;
import pe.com.mivoto.service.infrastructure.persistence.mappers.VoteRecordEntityMapper;
import pe.com.mivoto.service.infrastructure.persistence.repositories.JpaVoteRecordRepository;
import pe.com.mivoto.service.infrastructure.persistence.repositories.JpaVoteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the Vote Output Port (VoteRepository) using the JPA
 * repository.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VoteRepositoryAdapter implements VoteRepository {

    private final JpaVoteRepository jpaVoteRepository;
    private final JpaVoteRecordRepository jpaVoteRecordRepository;
    private final VoteEntityMapper voteEntityMapper;
    private final VoteRecordEntityMapper voteRecordEntityMapper;

    @Override
    public Vote save(Vote vote) {
        log.debug("Guardando voto: {}", vote.getVoteHash());
        VoteEntity entity = voteEntityMapper.toEntity(vote);
        VoteEntity saved = jpaVoteRepository.save(entity);
        return voteEntityMapper.toDomain(saved);
    }

    @Override
    public Vote update(Vote vote) {
        log.debug("Actualizando voto: {}", vote.getId());
        VoteEntity entity = voteEntityMapper.toEntity(vote);
        VoteEntity updated = jpaVoteRepository.save(entity);
        return voteEntityMapper.toDomain(updated);
    }

    @Override
    public Optional<Vote> findById(Long id) {
        return jpaVoteRepository.findById(id)
                .map(voteEntityMapper::toDomain);
    }

    @Override
    public Optional<Vote> findByVoteHash(String voteHash) {
        return jpaVoteRepository.findByVoteHash(voteHash)
                .map(voteEntityMapper::toDomain);
    }

    @Override
    public List<Vote> findByUserId(Long userId) {
        return jpaVoteRepository.findByUserId(userId).stream()
                .map(voteEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Vote> findByElectionId(Long electionId) {
        return jpaVoteRepository.findByElectionId(electionId).stream()
                .map(voteEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Vote> findByCandidateId(Long candidateId) {
        return jpaVoteRepository.findByCandidateId(candidateId).stream()
                .map(voteEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByUserIdAndElectionId(Long userId, Long electionId) {
        return jpaVoteRepository.existsByUserIdAndElectionId(userId, electionId);
    }

    @Override
    public Optional<Vote> findByUserIdAndElectionId(Long userId, Long electionId) {
        return jpaVoteRepository.findByUserIdAndElectionId(userId, electionId)
                .map(voteEntityMapper::toDomain);
    }

    @Override
    public Long countByElectionId(Long electionId) {
        return jpaVoteRepository.countByElectionId(electionId);
    }

    @Override
    public Long countByCandidateId(Long candidateId) {
        return jpaVoteRepository.countByCandidateId(candidateId);
    }

    @Override
    public List<Vote> findAll() {
        return jpaVoteRepository.findAll().stream()
                .map(voteEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Vote> findByStatus(VoteStatus status) {
        return jpaVoteRepository.findByStatus(status).stream()
                .map(voteEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Vote> findByVotedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return jpaVoteRepository.findByVotedAtBetween(startDate, endDate).stream()
                .map(voteEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaVoteRepository.deleteById(id);
    }

    @Override
    public VoteRecord saveVoteRecord(VoteRecord voteRecord) {
        log.debug("Guardando registro de voto: {}", voteRecord.getVoteHash());
        VoteRecordEntity entity = voteRecordEntityMapper.toEntity(voteRecord);
        VoteRecordEntity saved = jpaVoteRecordRepository.save(entity);
        return voteRecordEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<VoteRecord> findVoteRecordByHash(String voteHash) {
        return jpaVoteRecordRepository.findByVoteHash(voteHash)
                .map(voteRecordEntityMapper::toDomain);
    }

    @Override
    public List<VoteRecord> findVoteRecordsByUserId(Long userId) {
        return jpaVoteRecordRepository.findByUserId(userId).stream()
                .map(voteRecordEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<VoteRecord> findVoteRecordsByElectionId(Long electionId) {
        return jpaVoteRecordRepository.findByElectionId(electionId).stream()
                .map(voteRecordEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<VoteRecord> findAllVoteRecords() {
        return jpaVoteRecordRepository.findAll().stream()
                .map(voteRecordEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return jpaVoteRepository.count();
    }

    @Override
    public Map<String, Long> countByElectionIdGroupByParty(Long electionId) {
        return jpaVoteRepository.countByElectionIdGroupedByParty(electionId).stream()
                .collect(Collectors.toMap(
                        result -> (String) result[0],
                        result -> (Long) result[1]));
    }

    @Override
    public Map<Long, Long> countByElectionIdGroupByCandidate(Long electionId) {
        return jpaVoteRepository.countByElectionIdGroupedByCandidate(electionId).stream()
                .collect(Collectors.toMap(
                        result -> (Long) result[0],
                        result -> (Long) result[1]));
    }
}
