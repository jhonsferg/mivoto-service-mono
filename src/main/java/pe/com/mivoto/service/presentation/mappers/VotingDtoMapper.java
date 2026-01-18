package pe.com.mivoto.service.presentation.mappers;

import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.Vote;
import pe.com.mivoto.service.domain.model.VoteRecord;
import pe.com.mivoto.service.presentation.dto.response.VoteRecordDto;
import pe.com.mivoto.service.presentation.dto.response.VoteResponseDto;

/**
 * Mapper for Voting DTOs.
 * Converts between voting-related domain models and DTOs.
 */
@Component
public class VotingDtoMapper {

    /**
     * Converts Vote domain model to VoteResponse.
     *
     * @param vote The Vote domain model.
     * @return The VoteResponse DTO.
     */
    public VoteResponseDto toVoteResponse(Vote vote) {
        if (vote == null) {
            return null;
        }

        return VoteResponseDto.builder()
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

    /**
     * Converts VoteRecord domain model to VoteRecordDto.
     *
     * @param record The VoteRecord domain model.
     * @return The VoteRecordDto DTO.
     */
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
