package pe.com.mivoto.service.presentation.mappers;

import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.Candidate;
import pe.com.mivoto.service.presentation.dto.request.CreateCandidateRequestDto;
import pe.com.mivoto.service.presentation.dto.response.CandidateDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for Candidate DTOs.
 * Converts between Candidate domain model and DTOs.
 */
@Component
public class CandidateDtoMapper {

    /**
     * Converts CreateCandidateRequest to Candidate domain model.
     *
     * @param request The candidate creation request.
     * @return The Candidate domain model.
     */
    public Candidate toDomain(CreateCandidateRequestDto request) {
        return Candidate.builder()
                .electionId(request.getElectionId())
                .number(request.getNumber())
                .name(request.getName())
                .party(request.getParty())
                .description(request.getDescription())
                .photoUrl(request.getPhotoUrl())
                .build();
    }

    /**
     * Converts Candidate domain model to CandidateDto.
     *
     * @param candidate The Candidate domain model.
     * @return The CandidateDto.
     */
    public CandidateDto toCandidateDto(Candidate candidate) {
        if (candidate == null) {
            return null;
        }

        return CandidateDto.builder()
                .id(candidate.getId())
                .electionId(candidate.getElectionId())
                .number(candidate.getNumber())
                .name(candidate.getName())
                .party(candidate.getParty())
                .description(candidate.getDescription())
                .photoUrl(candidate.getPhotoUrl())
                .active(candidate.getActive())
                .voteCount(candidate.getVoteCount())
                .build();
    }

    /**
     * Converts a list of Candidate domain models to a list of CandidateDtos.
     *
     * @param candidates The list of Candidate domain models.
     * @return The list of CandidateDtos.
     */
    public List<CandidateDto> toCandidateDtoList(List<Candidate> candidates) {
        if (candidates == null) {
            return null;
        }

        return candidates.stream()
                .map(this::toCandidateDto)
                .collect(Collectors.toList());
    }
}
