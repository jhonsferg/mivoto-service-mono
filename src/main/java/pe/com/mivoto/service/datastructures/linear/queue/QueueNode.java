package pe.com.mivoto.service.datastructures.linear.queue;

import lombok.Data;

/**
 * Node class for queue data structure.
 * Each node contains data and a reference to the next node.
 *
 * @param <T> The type of data stored in the node.
 */
@Data
public class QueueNode<T> {
    /**
     * The data stored in this node.
     */
    private T data;

    /**
     * Reference to the next node in the queue.
     */
    private QueueNode<T> next;

    /**
     * Constructs a queue node with data and no next node.
     *
     * @param data The data to store in this node.
     */
    public QueueNode(T data) {
        this.data = data;
        this.next = null;
    }
}