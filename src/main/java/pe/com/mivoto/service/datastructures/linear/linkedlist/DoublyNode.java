package pe.com.mivoto.service.datastructures.linear.linkedlist;

import lombok.Data;

@Data
class DoublyNode<T> {
    private T data;
    private DoublyNode<T> next;
    private DoublyNode<T> prev;

    public DoublyNode(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
