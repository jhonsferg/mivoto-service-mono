package pe.com.mivoto.service.domain.ports.out;

import pe.com.mivoto.service.domain.model.Candidate;

import java.util.List;
import java.util.Optional;

public interface CandidateRepository {
    Candidate save(Candidate candidate);

    Candidate update(Candidate candidate);

    Optional<Candidate> findById(Long id);

    List<Candidate> findAll();

    List<Candidate> findByElectionId(Long electionId);

    Optional<Candidate> findByElectionIdAndNumber(Long electionId, Integer number);

    List<Candidate> findActiveByElectionId(Long electionId);

    List<Candidate> findByParty(String party);

    List<Candidate> findByNameContaining(String name);

    void deleteById(Long id);

    boolean existsByElectionIdAndNumber(Long electionId, Integer number);

    Long countByElectionId(Long electionId);

    List<Candidate> findByElectionIdOrderByVoteCountDesc(Long electionId);
}
