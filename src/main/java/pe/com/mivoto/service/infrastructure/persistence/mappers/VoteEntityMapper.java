package pe.com.mivoto.service.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.Vote;
import pe.com.mivoto.service.infrastructure.persistence.entities.VoteEntity;

/**
 * Mapper for Vote entity.
 * Converts between Vote domain model and VoteEntity.
 */
@Component
public class VoteEntityMapper {

    /**
     * Converts VoteEntity to Vote domain model.
     *
     * @param entity The VoteEntity.
     * @return The Vote domain model.
     */
    public Vote toDomain(VoteEntity entity) {
        if (entity == null) {
            return null;
        }

        return Vote.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .electionId(entity.getElectionId())
                .candidateId(entity.getCandidateId())
                .voteHash(entity.getVoteHash())
                .status(entity.getStatus())
                .verified(entity.getVerified())
                .verificationCode(entity.getVerificationCode())
                .votedAt(entity.getVotedAt())
                .verifiedAt(entity.getVerifiedAt())
                .ipAddress(entity.getIpAddress())
                .userAgent(entity.getUserAgent())
                .build();
    }

    /**
     * Converts Vote domain model to VoteEntity.
     *
     * @param domain The Vote domain model.
     * @return The VoteEntity.
     */
    public VoteEntity toEntity(Vote domain) {
        if (domain == null) {
            return null;
        }

        return VoteEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .electionId(domain.getElectionId())
                .candidateId(domain.getCandidateId())
                .voteHash(domain.getVoteHash())
                .status(domain.getStatus())
                .verified(domain.getVerified())
                .verificationCode(domain.getVerificationCode())
                .votedAt(domain.getVotedAt())
                .verifiedAt(domain.getVerifiedAt())
                .ipAddress(domain.getIpAddress())
                .userAgent(domain.getUserAgent())
                .build();
    }
}
