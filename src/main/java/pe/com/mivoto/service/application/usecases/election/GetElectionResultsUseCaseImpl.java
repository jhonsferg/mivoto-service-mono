package pe.com.mivoto.service.application.usecases.election;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.ElectionManagementService;
import pe.com.mivoto.service.domain.model.Candidate;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetElectionResultsUseCaseImpl {
    private final ElectionManagementService electionService;

    public Map<Candidate, Long> execute(Long electionId) {
        log.info("Ejecutando caso de uso: GetElectionResults - ID: {}", electionId);

        return this.electionService.getElectionResults(electionId);
    }
}
