package pe.com.mivoto.service.presentation.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.Candidate;
import pe.com.mivoto.service.domain.model.Election;
import pe.com.mivoto.service.presentation.dto.request.CreateElectionRequest;
import pe.com.mivoto.service.presentation.dto.response.ElectionResponse;
import pe.com.mivoto.service.presentation.dto.response.ElectionResultsResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ElectionDtoMapper {
    private final CandidateDtoMapper candidateDtoMapper;

    public Election toDomain(CreateElectionRequest request) {
        return Election.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .maxVotesPerUser(request.getMaxVotesPerUser())
                .allowsBlankVote(request.getAllowsBlankVote())
                .requiresVerification(request.getRequiresVerification())
                .build();
    }

    public ElectionResponse toElectionResponse(Election election, boolean hasVoted) {
        if (election == null) {
            return null;
        }

        return ElectionResponse.builder()
                .id(election.getId())
                .title(election.getTitle())
                .description(election.getDescription())
                .status(election.getStatus())
                .startDate(election.getStartDate())
                .endDate(election.getEndDate())
                .maxVotesPerUser(election.getMaxVotesPerUser())
                .allowsBlankVote(election.getAllowsBlankVote())
                .requiresVerification(election.getRequiresVerification())
                .candidates(candidateDtoMapper.toCandidateDtoList(election.getCandidates()))
                .hasVoted(hasVoted)
                .createdAt(election.getCreatedAt())
                .build();
    }

    public ElectionResultsResponse toElectionResultsResponse(Election election, Map<Candidate, Long> results) {
        Long totalVotes = results.values().stream()
                .mapToLong(Long::longValue)
                .sum();

        List<ElectionResultsResponse.CandidateResultDto> candidateResults = results.entrySet().stream()
                .map(entry -> {
                    Candidate candidate = entry.getKey();
                    Long votes = entry.getValue();
                    Double percentage = totalVotes > 0 ?
                            (votes.doubleValue() / totalVotes.doubleValue()) * 100 : 0.0;

                    return ElectionResultsResponse.CandidateResultDto.builder()
                            .candidateId(candidate.getId())
                            .candidateName(candidate.getName())
                            .party(candidate.getParty())
                            .number(candidate.getNumber())
                            .votes(votes)
                            .percentage(percentage)
                            .build();
                })
                .sorted((a, b) -> b.getVotes().compareTo(a.getVotes()))
                .collect(Collectors.toList());

        return ElectionResultsResponse.builder()
                .electionId(election.getId())
                .electionTitle(election.getTitle())
                .totalVotes(totalVotes)
                .results(candidateResults)
                .build();
    }
}
