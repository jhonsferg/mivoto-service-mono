package pe.com.mivoto.service.datastructures.linear.queue;

public class PriorityQueue<T> {
    private PriorityNode<T> front;
    private int size;

    public PriorityQueue() {
        this.front = null;
        this.size = 0;
    }

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

    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Cola vacía");
        }

        T data = this.front.getData();
        this.front = this.front.getNext();
        this.size--;

        return data;
    }

    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Cola vacía");
        }
        return this.front.getData();
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }
}
