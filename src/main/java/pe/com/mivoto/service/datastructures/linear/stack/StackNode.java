package pe.com.mivoto.service.datastructures.linear.stack;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Node class for Stack data structure.
 * Each node contains data and a reference to the next node.
 *
 * @param <T> The type of data stored in the node.
 */
@Data
@AllArgsConstructor
public class StackNode<T> {
    /**
     * The data stored in this node.
     */
    private T data;

    /**
     * Reference to the next node in the stack.
     */
    private StackNode<T> next;

    /**
     * Constructs a node with data and no next node.
     *
     * @param data The data to store in this node.
     */
    public StackNode(T data) {
        this.data = data;
        this.next = null;
    }
}
