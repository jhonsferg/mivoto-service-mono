package pe.com.mivoto.service.presentation.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.Election;
import pe.com.mivoto.service.infrastructure.persistence.entities.ElectionEntity;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ElectionMapper {
    private final CandidateMapper candidateMapper;

    public Election toDomain(ElectionEntity entity) {
        if (entity == null) {
            return null;
        }

        Election election = Election.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .maxVotesPerUser(entity.getMaxVotesPerUser())
                .allowsBlankVote(entity.getAllowsBlankVote())
                .requiresVerification(entity.getRequiresVerification())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .build();

        if (entity.getCandidates() != null && !entity.getCandidates().isEmpty()) {
            election.setCandidates(
                    entity.getCandidates().stream()
                            .map(candidateMapper::toDomain)
                            .collect(Collectors.toList())
            );
        }

        return election;
    }

    public ElectionEntity toEntity(Election domain) {
        if (domain == null) {
            return null;
        }

        ElectionEntity entity = ElectionEntity.builder()
                .id(domain.getId())
                .title(domain.getTitle())
                .description(domain.getDescription())
                .status(domain.getStatus())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .maxVotesPerUser(domain.getMaxVotesPerUser())
                .allowsBlankVote(domain.getAllowsBlankVote())
                .requiresVerification(domain.getRequiresVerification())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .createdBy(domain.getCreatedBy())
                .build();

        if (domain.getCandidates() != null && !domain.getCandidates().isEmpty()) {
            entity.setCandidates(
                    domain.getCandidates().stream()
                            .map(candidateMapper::toEntity)
                            .peek(c -> c.setElection(entity))
                            .collect(Collectors.toList())
            );
        }

        return entity;
    }
}
