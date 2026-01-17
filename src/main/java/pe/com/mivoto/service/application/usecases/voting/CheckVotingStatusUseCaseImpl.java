package pe.com.mivoto.service.application.usecases.voting;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.VotingService;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckVotingStatusUseCaseImpl {
    private final VotingService votingService;

    public boolean execute(Long userId, Long electionId) {
        log.debug("Verificando estado de votación - Usuario: {}, Elección: {}", userId, electionId);
        return votingService.hasVoted(userId, electionId);
    }
}
