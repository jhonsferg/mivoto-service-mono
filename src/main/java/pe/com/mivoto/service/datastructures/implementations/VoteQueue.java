package pe.com.mivoto.service.datastructures.implementations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.datastructures.linear.queue.CustomQueueImpl;
import pe.com.mivoto.service.domain.model.Vote;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Specialized queue for managing the buffer of votes to be processed.
 * Uses {@link CustomQueueImpl} as the underlying storage for vote items.
 */
@Slf4j
@Component
public class VoteQueue {
    private final CustomQueueImpl<VoteQueueItem> queue;
    private long processedCount;

    public VoteQueue() {
        this.queue = new CustomQueueImpl<>();
        this.processedCount = 0;
    }

    /**
     * Enqueues a vote with its entry timestamp.
     *
     * @param vote The vote object.
     */
    public void enqueueVote(Vote vote) {
        VoteQueueItem item = new VoteQueueItem(vote, LocalDateTime.now(), generateQueueId());
        this.queue.enqueue(item);
        log.info("Voto encolado - ID: {}, Elección: {}", vote.getId(), vote.getElectionId());
    }

    /**
     * Deserializes and removes the next vote from the queue.
     *
     * @return The Vote object, or null if empty.
     */
    public Vote dequeueVote() {
        if (this.queue.isEmpty()) {
            log.warn("Intento de desencolar de cola vacía");
            return null;
        }

        VoteQueueItem item = this.queue.dequeue();
        this.processedCount++;

        log.info("Voto desencolado - ID: {}, Tiempo en cola: {}ms", item.getVote().getId(),
                calculateQueueTime(item.getEnqueuedAt()));
        return item.getVote();
    }

    public Vote peekNextVote() {
        if (this.queue.isEmpty()) {
            return null;
        }

        return this.queue.peek().getVote();
    }

    public List<Vote> processAllVotes() {
        List<Vote> processedVotes = new ArrayList<>();

        while (!this.queue.isEmpty()) {
            Vote vote = dequeueVote();
            processedVotes.add(vote);
        }

        log.info("Procesados {} votos de la cola", processedVotes.size());
        return processedVotes;
    }

    public int size() {
        return this.queue.size();
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    public QueueStatistics getStatistics() {
        return new QueueStatistics(this.queue.size(), this.processedCount, LocalDateTime.now());
    }

    private String generateQueueId() {
        return "Q-" + System.currentTimeMillis() + "-" + this.queue.size();
    }

    private long calculateQueueTime(LocalDateTime enqueuedAt) {
        return Duration.between(enqueuedAt, LocalDateTime.now()).toMillis();
    }

    @Data
    @AllArgsConstructor
    public static class VoteQueueItem {
        private Vote vote;
        private LocalDateTime enqueuedAt;
        private String queueId;
    }

    @Data
    @AllArgsConstructor
    public static class QueueStatistics {
        private int currentSize;
        private long totalProcessed;
        private LocalDateTime timestamp;
    }
}
