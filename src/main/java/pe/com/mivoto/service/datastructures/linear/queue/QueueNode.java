package pe.com.mivoto.service.datastructures.linear.queue;

import lombok.Data;

@Data
public class QueueNode<T> {
    private T data;
    private QueueNode<T> next;

    public QueueNode(T data) {
        this.data = data;
        this.next = null;
    }
}