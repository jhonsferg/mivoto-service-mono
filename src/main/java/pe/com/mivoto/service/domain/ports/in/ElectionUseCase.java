package pe.com.mivoto.service.domain.ports.in;

import pe.com.mivoto.service.domain.model.Candidate;
import pe.com.mivoto.service.domain.model.Election;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ElectionUseCase {
    Election createElection(Election election, Long createdBy);

    Election updateElection(Long electionId, Election election);

    Election getElectionById(Long electionId);

    List<Election> getActiveElections();

    List<Election> getElectionsByStatus(pe.com.mivoto.service.domain.enums.ElectionStatus status);

    void startElection(Long electionId);

    void closeElection(Long electionId);

    void cancelElection(Long electionId, String reason);

    Map<Candidate, Long> getElectionResults(Long electionId);

    ElectionStatistics getElectionStatistics(Long electionId);

    List<Election> findElectionsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    record ElectionStatistics(
            Long totalVotes,
            Long totalValidVotes,
            Long totalInvalidVotes,
            Double participationRate,
            Candidate leadingCandidate
    ) {
    }
}