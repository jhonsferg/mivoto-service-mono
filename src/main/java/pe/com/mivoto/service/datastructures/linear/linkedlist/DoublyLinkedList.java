package pe.com.mivoto.service.datastructures.linear.linkedlist;

public class DoublyLinkedList<T> {
    private DoublyNode<T> head;
    private DoublyNode<T> tail;
    private int size;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

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

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }
}
