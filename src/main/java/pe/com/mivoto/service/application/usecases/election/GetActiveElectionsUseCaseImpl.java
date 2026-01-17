package pe.com.mivoto.service.application.usecases.election;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.ElectionManagementService;
import pe.com.mivoto.service.domain.model.Election;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetActiveElectionsUseCaseImpl {
    private final ElectionManagementService electionService;

    public List<Election> execute() {
        log.info("Ejecutando caso de uso: GetActiveElections");

        return this.electionService.getActiveElections();
    }
}
