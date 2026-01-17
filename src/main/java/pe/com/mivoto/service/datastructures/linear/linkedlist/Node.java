package pe.com.mivoto.service.datastructures.linear.linkedlist;

import lombok.Data;

@Data
public class Node<T> {
    private T data;
    private Node<T> next;

    public Node(T data) {
        this.data = data;
        this.next = null;
    }

    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }
}
