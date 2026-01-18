package pe.com.mivoto.service.application.usecases.election;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.ElectionManagementService;
import pe.com.mivoto.service.domain.model.Election;

import java.util.List;

/**
 * Use case implementation for retrieving active elections.
 * Wraps {@link ElectionManagementService#getActiveElections} logic.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GetActiveElectionsUseCaseImpl {
    private final ElectionManagementService electionService;

    /**
     * Executes the get active elections use case.
     *
     * @return List of active elections.
     */
    public List<Election> execute() {
        log.info("Ejecutando caso de uso: GetActiveElections");

        return this.electionService.getActiveElections();
    }
}
