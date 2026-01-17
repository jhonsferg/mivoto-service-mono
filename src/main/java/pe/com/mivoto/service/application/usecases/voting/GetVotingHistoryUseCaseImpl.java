package pe.com.mivoto.service.application.usecases.voting;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.VotingService;
import pe.com.mivoto.service.domain.model.VoteRecord;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetVotingHistoryUseCaseImpl {
    private final VotingService votingService;

    public List<VoteRecord> execute(Long userId) {
        log.info("Ejecutando caso de uso: GetVotingHistory - Usuario: {}", userId);
        return this.votingService.getVotingHistory(userId);
    }
}
