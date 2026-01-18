package pe.com.mivoto.service.datastructures.linear.queue;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Node class for priority queue data structure.
 * Each node contains data, priority level, and a reference to the next node.
 * Lower priority values indicate higher priority.
 *
 * @param <T> The type of data stored in the node.
 */
@Data
@AllArgsConstructor
class PriorityNode<T> {
    /**
     * The data stored in this node.
     */
    private T data;

    /**
     * The priority of this node. Lower values have higher priority.
     */
    private int priority;

    /**
     * Reference to the next node in the queue.
     */
    private PriorityNode<T> next;
}
