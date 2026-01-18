package pe.com.mivoto.service.datastructures.linear.queue;

import lombok.extern.slf4j.Slf4j;
import pe.com.mivoto.service.datastructures.interfaces.CustomQueue;

import java.util.NoSuchElementException;

/**
 * Custom implementation of a FIFO (First-In-First-Out) queue.
 * Implements the CustomQueue interface using a linked list structure.
 *
 * @param <T> The type of elements stored in the queue.
 */
@Slf4j
public class CustomQueueImpl<T> implements CustomQueue<T> {
    private QueueNode<T> front;
    private QueueNode<T> rear;
    private int size;

    /**
     * Constructs an empty queue.
     */
    public CustomQueueImpl() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    /**
     * Inserts the specified element into this queue.
     *
     * @param element The element to add.
     */
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

    /**
     * Retrieves and removes the head of this queue.
     *
     * @return The head of this queue.
     * @throws NoSuchElementException if this queue is empty.
     */
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

    /**
     * Retrieves, but does not remove, the head of this queue.
     *
     * @return The head of this queue.
     * @throws NoSuchElementException if this queue is empty.
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("La cola está vacía");
        }

        return this.front.getData();
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue contains no elements.
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Returns the number of elements in this queue.
     *
     * @return The size of the queue.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Removes all elements from the queue.
     */
    @Override
    public void clear() {
        this.front = null;
        this.rear = null;
        this.size = 0;
        log.debug("Cola limpiada");
    }

    /**
     * Returns a string representation of the queue.
     *
     * @return String representation showing front to rear.
     */
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
