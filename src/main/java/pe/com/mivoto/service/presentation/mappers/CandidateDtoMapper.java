package pe.com.mivoto.service.presentation.mappers;

import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.Candidate;
import pe.com.mivoto.service.presentation.dto.request.CreateCandidateRequest;
import pe.com.mivoto.service.presentation.dto.response.CandidateDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CandidateDtoMapper {

    public Candidate toDomain(CreateCandidateRequest request) {
        return Candidate.builder()
                .electionId(request.getElectionId())
                .number(request.getNumber())
                .name(request.getName())
                .party(request.getParty())
                .description(request.getDescription())
                .photoUrl(request.getPhotoUrl())
                .build();
    }

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

    public List<CandidateDto> toCandidateDtoList(List<Candidate> candidates) {
        if (candidates == null) {
            return null;
        }

        return candidates.stream()
                .map(this::toCandidateDto)
                .collect(Collectors.toList());
    }
}
