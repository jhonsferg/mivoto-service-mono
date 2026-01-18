package pe.com.mivoto.service.datastructures.linear.linkedlist;

import lombok.Data;

/**
 * Node class for doubly linked list data structure.
 * Each node contains data and references to both next and previous nodes.
 *
 * @param <T> The type of data stored in the node.
 */
@Data
class DoublyNode<T> {
    /**
     * The data stored in this node.
     */
    private T data;

    /**
     * Reference to the next node in the list.
     */
    private DoublyNode<T> next;

    /**
     * Reference to the previous node in the list.
     */
    private DoublyNode<T> prev;

    /**
     * Constructs a doubly node with data and no connections.
     *
     * @param data The data to store in this node.
     */
    public DoublyNode(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
