package pe.com.mivoto.service.datastructures.linear.queue;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class PriorityNode<T> {
    private T data;
    private int priority;
    private PriorityNode<T> next;
}
