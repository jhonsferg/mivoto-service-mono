package pe.com.mivoto.service.application.usecases.voting;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.VotingService;
import pe.com.mivoto.service.domain.model.Vote;

@Slf4j
@Component
@RequiredArgsConstructor
public class CastVoteUseCaseImpl {
    private final VotingService votingService;

    public Vote execute(Long userId, Long electionId, Long candidateId, String ipAddress, String userAgent) {
        log.info("Ejecutando caso de uso: CastVote - Usuario: {}, Elección: {}", userId, electionId);
        return this.votingService.castVote(userId, electionId, candidateId, ipAddress, userAgent);
    }
}
