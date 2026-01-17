package pe.com.mivoto.service.presentation.mappers;

import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.Vote;
import pe.com.mivoto.service.infrastructure.persistence.entities.VoteEntity;

@Component
public class VoteMapper {
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
