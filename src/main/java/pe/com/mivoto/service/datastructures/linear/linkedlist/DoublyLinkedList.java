package pe.com.mivoto.service.datastructures.linear.linkedlist;

/**
 * Custom implementation of a doubly linked list.
 * Each node maintains references to both next and previous nodes,
 * allowing efficient bidirectional traversal and removal operations.
 *
 * @param <T> The type of elements stored in the list.
 */
public class DoublyLinkedList<T> {
    private DoublyNode<T> head;
    private DoublyNode<T> tail;
    private int size;

    /**
     * Constructs an empty doubly linked list.
     */
    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Adds an element at the beginning of the list.
     *
     * @param element The element to add.
     */
    public void addFirst(T element) {
        DoublyNode<T> newNode = new DoublyNode<>(element);

        if (isEmpty()) {
            this.head = this.tail = newNode;
        } else {
            newNode.setNext(this.head);
            this.head.setPrev(newNode);
            this.head = newNode;
        }

        this.size++;
    }

    /**
     * Adds an element at the end of the list.
     *
     * @param element The element to add.
     */
    public void addLast(T element) {
        DoublyNode<T> newNode = new DoublyNode<>(element);

        if (isEmpty()) {
            this.head = this.tail = newNode;
        } else {
            this.tail.setNext(newNode);
            newNode.setPrev(this.tail);
            this.tail = newNode;
        }

        this.size++;
    }

    /**
     * Removes and returns the first element from the list.
     *
     * @return The removed element.
     * @throws IllegalStateException if the list is empty.
     */
    public T removeFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("Lista vacía");
        }

        T data = this.head.getData();
        this.head = this.head.getNext();

        if (this.head != null) {
            this.head.setPrev(null);
        } else {
            this.tail = null;
        }

        this.size--;
        return data;
    }

    /**
     * Removes and returns the last element from the list.
     *
     * @return The removed element.
     * @throws IllegalStateException if the list is empty.
     */
    public T removeLast() {
        if (isEmpty()) {
            throw new IllegalStateException("Lista vacía");
        }

        T data = this.tail.getData();
        this.tail = this.tail.getPrev();

        if (this.tail != null) {
            this.tail.setNext(null);
        } else {
            this.head = null;
        }

        this.size--;
        return data;
    }

    /**
     * Checks if the list is empty.
     *
     * @return true if the list contains no elements, false otherwise.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return The size of the list.
     */
    public int size() {
        return this.size;
    }
}
