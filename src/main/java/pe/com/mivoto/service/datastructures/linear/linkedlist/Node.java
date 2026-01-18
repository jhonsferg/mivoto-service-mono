package pe.com.mivoto.service.datastructures.linear.linkedlist;

import lombok.Data;

/**
 * Node class for singly linked list data structure.
 * Each node contains data and a reference to the next node.
 *
 * @param <T> The type of data stored in the node.
 */
@Data
public class Node<T> {
    /**
     * The data stored in this node.
     */
    private T data;

    /**
     * Reference to the next node in the list.
     */
    private Node<T> next;

    /**
     * Constructs a node with data and no next node.
     *
     * @param data The data to store in this node.
     */
    public Node(T data) {
        this.data = data;
        this.next = null;
    }

    /**
     * Constructs a node with data and a reference to the next node.
     *
     * @param data The data to store in this node.
     * @param next The next node in the list.
     */
    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }
}
