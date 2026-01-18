package pe.com.mivoto.service.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.Candidate;
import pe.com.mivoto.service.infrastructure.persistence.entities.CandidateEntity;

/**
 * Mapper for Candidate entity.
 * Converts between Candidate domain model and CandidateEntity.
 */
@Component
public class CandidateEntityMapper {

    /**
     * Converts CandidateEntity to Candidate domain model.
     *
     * @param entity The CandidateEntity.
     * @return The Candidate domain model.
     */
    public Candidate toDomain(CandidateEntity entity) {
        if (entity == null) {
            return null;
        }

        return Candidate.builder()
                .id(entity.getId())
                .electionId(entity.getElection() != null ? entity.getElection().getId() : null)
                .number(entity.getNumber())
                .name(entity.getName())
                .party(entity.getParty())
                .description(entity.getDescription())
                .photoUrl(entity.getPhotoUrl())
                .active(entity.getActive())
                .voteCount(entity.getVoteCount())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    /**
     * Converts Candidate domain model to CandidateEntity.
     *
     * @param domain The Candidate domain model.
     * @return The CandidateEntity.
     */
    public CandidateEntity toEntity(Candidate domain) {
        if (domain == null) {
            return null;
        }

        return CandidateEntity.builder()
                .id(domain.getId())
                .number(domain.getNumber())
                .name(domain.getName())
                .party(domain.getParty())
                .description(domain.getDescription())
                .photoUrl(domain.getPhotoUrl())
                .active(domain.getActive())
                .voteCount(domain.getVoteCount())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
