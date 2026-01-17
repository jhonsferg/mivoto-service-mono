package pe.com.mivoto.service.application.usecases.election;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.ElectionManagementService;

@Slf4j
@Component
@RequiredArgsConstructor
public class CloseElectionUseCaseImpl {
    private final ElectionManagementService electionService;

    public void execute(Long electionId) {
        log.info("Ejecutando caso de uso: CloseElection - ID: {}", electionId);
        this.electionService.closeElection(electionId);
    }
}
