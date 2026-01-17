package pe.com.mivoto.service.application.usecases.voting;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.VotingService;
import pe.com.mivoto.service.domain.model.VoteRecord;

@Slf4j
@Component
@RequiredArgsConstructor
public class VerifyVoteUseCaseImpl {
    private final VotingService votingService;

    public VoteRecord execute(String voteHash) {
        log.info("Ejecutando caso de uso: VerifyVote - Hash: {}", voteHash);
        return this.votingService.verifyVote(voteHash);
    }
}
