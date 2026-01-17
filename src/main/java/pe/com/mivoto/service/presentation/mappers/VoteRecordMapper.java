package pe.com.mivoto.service.presentation.mappers;

import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.VoteRecord;
import pe.com.mivoto.service.infrastructure.persistence.entities.VoteRecordEntity;

@Component
public class VoteRecordMapper {
    public VoteRecord toDomain(VoteRecordEntity entity) {
        if (entity == null) {
            return null;
        }

        return VoteRecord.builder()
                .id(entity.getId())
                .voteId(entity.getVoteId())
                .userId(entity.getUserId())
                .electionId(entity.getElectionId())
                .voteHash(entity.getVoteHash())
                .timestamp(entity.getTimestamp())
                .verified(entity.getVerified())
                .blockchainHash(entity.getBlockchainHash())
                .build();
    }

    public VoteRecordEntity toEntity(VoteRecord domain) {
        if (domain == null) {
            return null;
        }

        return VoteRecordEntity.builder()
                .id(domain.getId())
                .voteId(domain.getVoteId())
                .userId(domain.getUserId())
                .electionId(domain.getElectionId())
                .voteHash(domain.getVoteHash())
                .timestamp(domain.getTimestamp())
                .verified(domain.getVerified())
                .blockchainHash(domain.getBlockchainHash())
                .build();
    }
}
