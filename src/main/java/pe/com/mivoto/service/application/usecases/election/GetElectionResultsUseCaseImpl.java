package pe.com.mivoto.service.application.usecases.election;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.ElectionManagementService;
import pe.com.mivoto.service.domain.model.Candidate;

import java.util.Map;

/**
 * Use case implementation for retrieving election results.
 * Wraps {@link ElectionManagementService#getElectionResults} logic.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GetElectionResultsUseCaseImpl {
    private final ElectionManagementService electionService;

    /**
     * Executes the get election results use case.
     *
     * @param electionId The election ID.
     * @return Map of Candidate to vote count.
     */
    public Map<Candidate, Long> execute(Long electionId) {
        log.info("Ejecutando caso de uso: GetElectionResults - ID: {}", electionId);

        return this.electionService.getElectionResults(electionId);
    }
}
