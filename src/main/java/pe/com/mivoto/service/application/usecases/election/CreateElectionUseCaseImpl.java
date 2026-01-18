package pe.com.mivoto.service.application.usecases.election;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.ElectionManagementService;
import pe.com.mivoto.service.domain.model.Election;

/**
 * Use case implementation for creating elections.
 * Wraps {@link ElectionManagementService#createElection} logic.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CreateElectionUseCaseImpl {
    private final ElectionManagementService electionService;

    /**
     * Executes the create election use case.
     *
     * @param election  The election to create.
     * @param createdBy The user ID creating the election.
     * @return The created Election.
     */
    public Election execute(Election election, Long createdBy) {
        log.info("Ejecutando caso de uso: CreateElection - Título: {}", election.getTitle());

        return this.electionService.createElection(election, createdBy);
    }
}
