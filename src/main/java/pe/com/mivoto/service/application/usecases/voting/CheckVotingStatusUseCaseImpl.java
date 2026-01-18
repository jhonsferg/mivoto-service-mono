package pe.com.mivoto.service.application.usecases.voting;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.VotingService;

/**
 * Use case implementation for checking voting status.
 * Wraps {@link VotingService#hasVoted} logic.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CheckVotingStatusUseCaseImpl {
    private final VotingService votingService;

    /**
     * Executes the check voting status use case.
     *
     * @param userId     The user ID.
     * @param electionId The election ID.
     * @return true if the user has voted (or attempts are exhausted), false
     * otherwise.
     */
    public boolean execute(Long userId, Long electionId) {
        log.debug("Verificando estado de votación - Usuario: {}, Elección: {}", userId, electionId);
        return votingService.hasVoted(userId, electionId);
    }
}
