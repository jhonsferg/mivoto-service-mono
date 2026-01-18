package pe.com.mivoto.service.application.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pe.com.mivoto.service.datastructures.implementations.VoteQueue;
import pe.com.mivoto.service.domain.model.Vote;

/**
 * Service for asynchronous processing of queued votes.
 * Consumes votes from the {@link VoteQueue} and processes them in batches.
 * Ensures high throughput by offloading vote persistence from the main thread.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VoteProcessingService {
    private final VoteQueue voteQueue;
    private final VotingService votingService;

    /**
     * Scheduled task to process votes from the queue.
     * Runs every 5 seconds.
     * Processes up to 100 votes per run to prevent system overload.
     */
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

    /**
     * Processes a single vote asynchronously.
     * If processing fails, re-enqueues the vote for retry.
     *
     * @param vote The vote to process.
     */
    @Async
    public void processVoteAsync(Vote vote) {
        try {
            log.debug("Procesando voto asíncrono - ID: {}", vote.getId());
            votingService.processVote(vote);
        } catch (Exception e) {
            log.error("Error procesando voto asíncrono", e);
            voteQueue.enqueueVote(vote);
        }
    }

    /**
     * Retrieves current statistics of the vote queue, such as size and processing
     * rate.
     *
     * @return QueueStatistics object with current metrics.
     */
    public VoteQueue.QueueStatistics getQueueStatistics() {
        return voteQueue.getStatistics();
    }
}
