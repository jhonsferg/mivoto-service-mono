package pe.com.mivoto.service.application.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pe.com.mivoto.service.datastructures.implementations.VoteQueue;
import pe.com.mivoto.service.domain.model.Vote;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteProcessingService {
    private final VoteQueue voteQueue;
    private final VotingService votingService;

    @Scheduled(fixedDelay = 5000)
    public void processVoteQueue() {
        if (voteQueue.isEmpty()) {
            return;
        }

        log.info("Procesando cola de votos - Tamaño: {}", voteQueue.size());

        int processed = 0;
        int maxBatchSize = 100;

        while (!voteQueue.isEmpty() && processed < maxBatchSize) {
            Vote vote = voteQueue.dequeueVote();
            if (vote != null) {
                processVoteAsync(vote);
                processed++;
            }
        }

        log.info("Procesados {} votos", processed);
    }

    @Async
    public void processVoteAsync(Vote vote) {
        try {
            log.debug("Procesando voto asíncrono - ID: {}", vote.getId());
        } catch (Exception e) {
            log.error("Error procesando voto asíncrono", e);
            voteQueue.enqueueVote(vote);
        }
    }

    public VoteQueue.QueueStatistics getQueueStatistics() {
        return voteQueue.getStatistics();
    }
}
