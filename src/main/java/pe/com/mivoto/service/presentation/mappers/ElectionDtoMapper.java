package pe.com.mivoto.service.presentation.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.Candidate;
import pe.com.mivoto.service.domain.model.Election;
import pe.com.mivoto.service.presentation.dto.request.CreateElectionRequestDto;
import pe.com.mivoto.service.presentation.dto.response.ElectionResponseDto;
import pe.com.mivoto.service.presentation.dto.response.ElectionResultsResponseDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Mapper for Election DTOs.
 * Converts between Election domain model and DTOs.
 */
@Component
@RequiredArgsConstructor
public class ElectionDtoMapper {
        private final CandidateDtoMapper candidateDtoMapper;

        /**
         * Converts CreateElectionRequest to Election domain model.
         *
         * @param request The election creation request.
         * @return The Election domain model.
         */
        public Election toDomain(CreateElectionRequestDto request) {
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

        /**
         * Converts Election domain model to ElectionResponse.
         *
         * @param election The Election domain model.
         * @param hasVoted Whether the current user has voted in this election.
         * @return The ElectionResponse DTO.
         */
        public ElectionResponseDto toElectionResponse(Election election, boolean hasVoted) {
                if (election == null) {
                        return null;
                }

                return ElectionResponseDto.builder()
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

        /**
         * Converts Election and results map to ElectionResultsResponse.
         *
         * @param election The Election domain model.
         * @param results  The map of candidates to vote counts.
         * @return The ElectionResultsResponse DTO.
         */
        public ElectionResultsResponseDto toElectionResultsResponse(Election election, Map<Candidate, Long> results) {
                Long totalVotes = results.values().stream()
                                .mapToLong(Long::longValue)
                                .sum();

                List<ElectionResultsResponseDto.CandidateResultDto> candidateResults = results.entrySet().stream()
                                .map(entry -> {
                                        Candidate candidate = entry.getKey();
                                        Long votes = entry.getValue();
                                        Double percentage = totalVotes > 0
                                                        ? (votes.doubleValue() / totalVotes.doubleValue()) * 100
                                                        : 0.0;

                                        return ElectionResultsResponseDto.CandidateResultDto.builder()
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

                return ElectionResultsResponseDto.builder()
                                .electionId(election.getId())
                                .electionTitle(election.getTitle())
                                .totalVotes(totalVotes)
                                .results(candidateResults)
                                .build();
        }
}
