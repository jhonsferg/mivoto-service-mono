package pe.com.mivoto.service.application.usecases.election;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.ElectionManagementService;

/**
 * Use case implementation for closing elections.
 * Wraps {@link ElectionManagementService#closeElection} logic.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CloseElectionUseCaseImpl {
    private final ElectionManagementService electionService;

    /**
     * Executes the close election use case.
     *
     * @param electionId The election ID.
     */
    public void execute(Long electionId) {
        log.info("Ejecutando caso de uso: CloseElection - ID: {}", electionId);
        this.electionService.closeElection(electionId);
    }
}
