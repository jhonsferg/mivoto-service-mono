package pe.com.mivoto.service.datastructures.linear.queue;

import lombok.extern.slf4j.Slf4j;
import pe.com.mivoto.service.datastructures.interfaces.CustomQueue;

import java.util.NoSuchElementException;

@Slf4j
public class CustomQueueImpl<T> implements CustomQueue<T> {
    private QueueNode<T> front;
    private QueueNode<T> rear;
    private int size;

    public CustomQueueImpl() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    @Override
    public void enqueue(T element) {
        QueueNode<T> newNode = new QueueNode<>(element);

        if (isEmpty()) {
            this.front = newNode;
        } else {
            this.rear.setNext(newNode);
        }
        this.rear = newNode;

        this.size++;
        log.debug("Elemento encolado. Tamaño actual: {}", this.size);
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("La cola está vacía");
        }

        T data = this.front.getData();
        this.front = this.front.getNext();

        if (this.front == null) {
            this.rear = null;
        }

        this.size--;
        log.debug("Elemento desencolado. Tamaño actual: {}", this.size);

        return data;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("La cola está vacía");
        }

        return this.front.getData();
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.front = null;
        this.rear = null;
        this.size = 0;
        log.debug("Cola limpiada");
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[Front -> ");
        QueueNode<T> current = this.front;

        while (current != null) {
            sb.append(current.getData());
            if (current.getNext() != null) {
                sb.append(", ");
            }
            current = current.getNext();
        }

        sb.append(" <- Rear]");
        return sb.toString();
    }
}
