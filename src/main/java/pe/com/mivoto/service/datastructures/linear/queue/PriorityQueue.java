package pe.com.mivoto.service.datastructures.linear.queue;

/**
 * Custom implementation of a priority queue.
 * Elements are dequeued in order of priority, with higher priority values
 * being dequeued first. Elements with equal priority follow FIFO order.
 *
 * @param <T> The type of elements stored in the queue.
 */
public class PriorityQueue<T> {
    private PriorityNode<T> front;
    private int size;

    /**
     * Constructs an empty priority queue.
     */
    public PriorityQueue() {
        this.front = null;
        this.size = 0;
    }

    /**
     * Adds an element to the queue with the specified priority.
     * Higher priority values are dequeued first.
     *
     * @param element  The element to enqueue.
     * @param priority The priority of the element (higher values = higher
     *                 priority).
     */
    public void enqueue(T element, int priority) {
        PriorityNode<T> newNode = new PriorityNode<>(element, priority, null);

        if (front == null || priority > front.getPriority()) {
            newNode.setNext(front);
            front = newNode;
        } else {
            PriorityNode<T> current = front;
            while (current.getNext() != null && current.getNext().getPriority() >= priority) {
                current = current.getNext();
            }

            newNode.setNext(current.getNext());
            current.setNext(newNode);
        }

        this.size++;
    }

    /**
     * Removes and returns the element with the highest priority.
     *
     * @return The element with the highest priority.
     * @throws IllegalStateException if the queue is empty.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Cola vacía");
        }

        T data = this.front.getData();
        this.front = this.front.getNext();
        this.size--;

        return data;
    }

    /**
     * Returns the element with the highest priority without removing it.
     *
     * @return The element with the highest priority.
     * @throws IllegalStateException if the queue is empty.
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Cola vacía");
        }
        return this.front.getData();
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue contains no elements, false otherwise.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return The size of the queue.
     */
    public int size() {
        return this.size;
    }
}
