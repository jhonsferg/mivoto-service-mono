package pe.com.mivoto.service.datastructures.linear.queue;

import pe.com.mivoto.service.domain.model.Vote;
import java.util.LinkedList;
import java.util.Queue;

public class VoteQueue {
    private final Queue<Vote> queue;

    public VoteQueue() {
        this.queue = new LinkedList<>();
    }

    public void enqueue(Vote vote) {
        queue.offer(vote);
    }

    public Vote dequeue() {
        return queue.poll();
    }

    public Vote peek() {
        return queue.peek();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }
}
