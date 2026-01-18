package pe.com.mivoto.service.datastructures.interfaces;

/**
 * Generic interface for custom queue data structures.
 * Defines basic queue operations following FIFO (First-In-First-Out) principle.
 *
 * @param <T> The type of elements stored in the queue.
 */
public interface CustomQueue<T> {
    /**
     * Adds an element to the end of the queue.
     *
     * @param element The element to enqueue.
     */
    void enqueue(T element);

    /**
     * Removes and returns the element at the front of the queue.
     *
     * @return The element at the front of the queue.
     * @throws IllegalStateException if the queue is empty.
     */
    T dequeue();

    /**
     * Returns the element at the front of the queue without removing it.
     *
     * @return The element at the front of the queue.
     * @throws IllegalStateException if the queue is empty.
     */
    T peek();

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue contains no elements, false otherwise.
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in the queue.
     *
     * @return The size of the queue.
     */
    int size();

    /**
     * Removes all elements from the queue.
     */
    void clear();
}