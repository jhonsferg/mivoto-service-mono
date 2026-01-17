package pe.com.mivoto.service.domain.ports.out;

import pe.com.mivoto.service.domain.enums.VoteStatus;
import pe.com.mivoto.service.domain.model.Vote;
import pe.com.mivoto.service.domain.model.VoteRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VoteRepository {
    Vote save(Vote vote);

    Vote update(Vote vote);

    Optional<Vote> findById(Long id);

    Optional<Vote> findByVoteHash(String voteHash);

    List<Vote> findByUserId(Long userId);

    List<Vote> findByElectionId(Long electionId);

    List<Vote> findByCandidateId(Long candidateId);

    boolean existsByUserIdAndElectionId(Long userId, Long electionId);

    Optional<Vote> findByUserIdAndElectionId(Long userId, Long electionId);

    Long countByElectionId(Long electionId);

    Long countByCandidateId(Long candidateId);

    List<Vote> findAll();

    List<Vote> findByStatus(VoteStatus status);

    List<Vote> findByVotedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    void deleteById(Long id);

    VoteRecord saveVoteRecord(VoteRecord voteRecord);

    Optional<VoteRecord> findVoteRecordByHash(String voteHash);

    List<VoteRecord> findVoteRecordsByUserId(Long userId);

    List<VoteRecord> findVoteRecordsByElectionId(Long electionId);

    List<VoteRecord> findAllVoteRecords();
}
