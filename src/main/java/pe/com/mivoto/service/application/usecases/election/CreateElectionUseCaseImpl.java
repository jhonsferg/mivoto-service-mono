package pe.com.mivoto.service.application.usecases.election;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.ElectionManagementService;
import pe.com.mivoto.service.domain.model.Election;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateElectionUseCaseImpl {
    private final ElectionManagementService electionService;

    public Election execute(Election election, Long createdBy) {
        log.info("Ejecutando caso de uso: CreateElection - Título: {}", election.getTitle());

        return this.electionService.createElection(election, createdBy);
    }
}
