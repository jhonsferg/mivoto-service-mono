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

/**
 * Application service for aggregating and retrieving system statistics.
 * Provides insights into system usage, election participation, and candidate
 * performance.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final UserRepository userRepository;
    private final ElectionRepository electionRepository;
    private final CandidateRepository candidateRepository;
    private final VoteRepository voteRepository;

    /**
     * Generates an overview of system-wide statistics.
     * Includes totals for users, elections, and votes.
     *
     * @return SystemStatistics record.
     */
    public SystemStatistics getSystemStatistics() {
        log.info("Generando estadísticas del sistema");

        Long totalUsers = this.userRepository.count();
        Long totalElections = this.electionRepository.count();
        Long activeElections = this.electionRepository
                .countByStatus(pe.com.mivoto.service.domain.enums.ElectionStatus.ACTIVE);
        Long totalVotes = this.voteRepository.findAll().stream().count();

        return new SystemStatistics(totalUsers, totalElections, activeElections, totalVotes, LocalDateTime.now());
    }

    /**
     * Retrieves detailed statistics for a specific election.
     * Includes vote distribution by party and identifying the leading candidate.
     *
     * @param electionId The election ID.
     * @return ElectionStatistics record.
     */
    public ElectionStatistics getElectionStatistics(Long electionId) {
        log.info("Generando estadísticas de elección: {}", electionId);

        Election election = this.electionRepository.findById(electionId)
                .orElseThrow(() -> new RuntimeException("Elección no encontrada"));
        Long totalVotes = this.voteRepository.countByElectionId(electionId);
        List<Candidate> candidates = this.candidateRepository.findByElectionIdOrderByVoteCountDesc(electionId);

        Candidate winner = candidates.isEmpty() ? null : candidates.get(0);
        Map<String, Long> votesByParty = new HashMap<>();
        for (Candidate candidate : candidates) {
            String party = candidate.getParty();
            Long votes = this.voteRepository.countByCandidateId(candidate.getId());
            votesByParty.merge(party, votes, Long::sum);
        }

        return new ElectionStatistics(electionId, election.getTitle(), totalVotes, candidates.size(), winner,
                votesByParty, LocalDateTime.now());
    }

    /**
     * Retrieves statistics per candidate for an election.
     * Calculates vote percentages for each candidate.
     *
     * @param electionId The election ID.
     * @return List of CandidateStatistics.
     */
    public List<CandidateStatistics> getCandidateStatistics(Long electionId) {
        log.info("Generando estadísticas de candidatos para elección: {}", electionId);

        List<Candidate> candidates = this.candidateRepository.findByElectionIdOrderByVoteCountDesc(electionId);
        Long totalVotes = this.voteRepository.countByElectionId(electionId);
        return candidates.stream()
                .map(candidate -> {
                    Long votes = this.voteRepository.countByCandidateId(candidate.getId());
                    Double percentage = totalVotes > 0 ? (votes.doubleValue() / totalVotes.doubleValue()) * 100 : 0.0;

                    return new CandidateStatistics(candidate.getId(), candidate.getName(), candidate.getParty(), votes,
                            percentage);
                })
                .toList();
    }

    /**
     * Analyzes voting activity by hour of day.
     * Useful for identifying peak voting times.
     *
     * @param electionId The election ID.
     * @return Map of Hour (0-23) to vote count.
     */
    public Map<Integer, Long> getVotingParticipationByHour(Long electionId) {
        Map<Integer, Long> participation = new HashMap<>();
        List<Vote> votes = this.voteRepository.findByElectionId(electionId);

        for (Vote vote : votes) {
            int hour = vote.getVotedAt().getHour();
            participation.merge(hour, 1L, Long::sum);
        }

        return participation;
    }

    /**
     * Record containing system-wide overview statistics.
     *
     * @param totalUsers      Total registered users in the system.
     * @param totalElections  Total elections created.
     * @param activeElections Number of elections currently in ACTIVE status.
     * @param totalVotes      Total number of votes cast system-wide.
     * @param generatedAt     Timestamp of when statistics were generated.
     */
    public record SystemStatistics(
            Long totalUsers,
            Long totalElections,
            Long activeElections,
            Long totalVotes,
            LocalDateTime generatedAt) {
    }

    /**
     * Record containing aggregated statistical data for a specific election.
     *
     * @param electionId       The unique identifier of the election.
     * @param electionTitle    The title of the election.
     * @param totalVotes       Total votes cast for this election.
     * @param totalCandidates  Number of candidates participating.
     * @param leadingCandidate The candidate currently with the most votes.
     * @param votesByParty     Map of political party names to their total vote
     *                         counts.
     * @param generatedAt      Timestamp of when statistics were generated.
     */
    public record ElectionStatistics(
            Long electionId,
            String electionTitle,
            Long totalVotes,
            Integer totalCandidates,
            Candidate leadingCandidate,
            Map<String, Long> votesByParty,
            LocalDateTime generatedAt) {
    }

    /**
     * Record containing performance metrics for an individual candidate.
     *
     * @param candidateId   The unique identifier of the candidate.
     * @param candidateName Full name of the candidate.
     * @param party         Political party affiliation.
     * @param votes         Current vote count for the candidate.
     * @param percentage    Percentage of total valid votes.
     */
    public record CandidateStatistics(
            Long candidateId,
            String candidateName,
            String party,
            Long votes,
            Double percentage) {
    }
}
