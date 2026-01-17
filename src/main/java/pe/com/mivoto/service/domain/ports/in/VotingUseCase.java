package pe.com.mivoto.service.domain.ports.in;

import pe.com.mivoto.service.domain.model.Vote;
import pe.com.mivoto.service.domain.model.VoteRecord;

import java.util.List;

public interface VotingUseCase {
    Vote castVote(Long userId, Long electionId, Long candidateId);

    VoteRecord verifyVote(String voteHash);

    boolean hasVoted(Long userId, Long electionId);

    List<VoteRecord> getVotingHistory(Long userId);

    List<Vote> getVotesByElection(Long electionId);

    Long countVotesByCandidate(Long candidateId);

    void invalidateVote(Long voteId, String reason);
}
