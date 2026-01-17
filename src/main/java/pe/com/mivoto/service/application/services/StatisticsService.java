package pe.com.mivoto.service.application.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.com.mivoto.service.domain.model.Candidate;
import pe.com.mivoto.service.domain.model.Election;
import pe.com.mivoto.service.domain.model.Vote;
import pe.com.mivoto.service.domain.ports.out.CandidateRepository;
import pe.com.mivoto.service.domain.ports.out.ElectionRepository;
import pe.com.mivoto.service.domain.ports.out.UserRepository;
import pe.com.mivoto.service.domain.ports.out.VoteRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final UserRepository userRepository;
    private final ElectionRepository electionRepository;
    private final CandidateRepository candidateRepository;
    private final VoteRepository voteRepository;

    public SystemStatistics getSystemStatistics() {
        log.info("Generando estadísticas del sistema");

        Long totalUsers = this.userRepository.count();
        Long totalElections = this.electionRepository.count();
        Long activeElections = this.electionRepository.countByStatus(pe.com.mivoto.service.domain.enums.ElectionStatus.ACTIVE);
        Long totalVotes = this.voteRepository.findAll().stream().count();

        return new SystemStatistics(totalUsers, totalElections, activeElections, totalVotes, LocalDateTime.now());
    }

    public ElectionStatistics getElectionStatistics(Long electionId) {
        log.info("Generando estadísticas de elección: {}", electionId);

        Election election = this.electionRepository.findById(electionId).orElseThrow(() -> new RuntimeException("Elección no encontrada"));
        Long totalVotes = this.voteRepository.countByElectionId(electionId);
        List<Candidate> candidates = this.candidateRepository.findByElectionIdOrderByVoteCountDesc(electionId);

        Candidate winner = candidates.isEmpty() ? null : candidates.get(0);
        Map<String, Long> votesByParty = new HashMap<>();
        for (Candidate candidate : candidates) {
            String party = candidate.getParty();
            Long votes = this.voteRepository.countByCandidateId(candidate.getId());
            votesByParty.merge(party, votes, Long::sum);
        }

        return new ElectionStatistics(electionId, election.getTitle(), totalVotes, candidates.size(), winner, votesByParty, LocalDateTime.now());
    }

    public List<CandidateStatistics> getCandidateStatistics(Long electionId) {
        log.info("Generando estadísticas de candidatos para elección: {}", electionId);

        List<Candidate> candidates = this.candidateRepository.findByElectionIdOrderByVoteCountDesc(electionId);
        Long totalVotes = this.voteRepository.countByElectionId(electionId);
        return candidates.stream()
                .map(candidate -> {
                    Long votes = this.voteRepository.countByCandidateId(candidate.getId());
                    Double percentage = totalVotes > 0 ? (votes.doubleValue() / totalVotes.doubleValue()) * 100 : 0.0;

                    return new CandidateStatistics(candidate.getId(), candidate.getName(), candidate.getParty(), votes, percentage);
                })
                .toList();
    }

    public Map<Integer, Long> getVotingParticipationByHour(Long electionId) {
        Map<Integer, Long> participation = new HashMap<>();
        List<Vote> votes = this.voteRepository.findByElectionId(electionId);

        for (Vote vote : votes) {
            int hour = vote.getVotedAt().getHour();
            participation.merge(hour, 1L, Long::sum);
        }

        return participation;
    }

    public record SystemStatistics(
            Long totalUsers,
            Long totalElections,
            Long activeElections,
            Long totalVotes,
            LocalDateTime generatedAt
    ) {
    }

    public record ElectionStatistics(
            Long electionId,
            String electionTitle,
            Long totalVotes,
            Integer totalCandidates,
            Candidate leadingCandidate,
            Map<String, Long> votesByParty,
            LocalDateTime generatedAt
    ) {
    }

    public record CandidateStatistics(
            Long candidateId,
            String candidateName,
            String party,
            Long votes,
            Double percentage
    ) {
    }
}
