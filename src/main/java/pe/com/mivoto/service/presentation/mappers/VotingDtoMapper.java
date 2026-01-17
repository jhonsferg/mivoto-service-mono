package pe.com.mivoto.service.presentation.mappers;

import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.Vote;
import pe.com.mivoto.service.domain.model.VoteRecord;
import pe.com.mivoto.service.presentation.dto.response.VoteRecordDto;
import pe.com.mivoto.service.presentation.dto.response.VoteResponse;

@Component
public class VotingDtoMapper {
    public VoteResponse toVoteResponse(Vote vote) {
        if (vote == null) {
            return null;
        }

        return VoteResponse.builder()
                .id(vote.getId())
                .electionId(vote.getElectionId())
                .candidateId(vote.getCandidateId())
                .voteHash(vote.getVoteHash())
                .status(vote.getStatus())
                .verified(vote.getVerified())
                .votedAt(vote.getVotedAt())
                .verificationCode(vote.getVerificationCode())
                .build();
    }

    public VoteRecordDto toVoteRecordDto(VoteRecord record) {
        if (record == null) {
            return null;
        }

        return VoteRecordDto.builder()
                .id(record.getId())
                .voteId(record.getVoteId())
                .electionId(record.getElectionId())
                .voteHash(record.getVoteHash())
                .timestamp(record.getTimestamp())
                .verified(record.getVerified())
                .build();
    }
}
