package pe.com.mivoto.service.datastructures.linear.linkedlist;

import lombok.extern.slf4j.Slf4j;
import pe.com.mivoto.service.datastructures.interfaces.CustomList;

import java.util.NoSuchElementException;

/**
 * Custom implementation of a singly linked list.
 * Implements the CustomList interface and provides efficient operations for
 * adding/removing elements at both ends.
 *
 * @param <T> The type of elements stored in the list.
 */
@Slf4j
public class CustomLinkedList<T> implements CustomList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * Constructs an empty linked list.
     */
    public CustomLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param element The element to add.
     */
    @Override
    public void add(T element) {
        addLast(element);
    }

    /**
     * Inserts the specified element at the beginning of this list.
     *
     * @param element The element to add.
     */
    @Override
    public void addFirst(T element) {
        Node<T> newNode = new Node<>(element);

        if (isEmpty()) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            newNode.setNext(this.head);
            this.head = newNode;
        }

        this.size++;
        log.debug("Elemento agregado al inicio. Tamaño actual: {}", this.size);
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param element The element to add.
     */
    @Override
    public void addLast(T element) {
        Node<T> newNode = new Node<>(element);

        if (isEmpty()) {
            this.head = newNode;
        } else {
            this.tail.setNext(newNode);
        }
        this.tail = newNode;

        this.size++;
        log.debug("Elemento agregado al final. Tamaño actual: {}", this.size);
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index The index of the element to return.
     * @return The element at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    @Override
    public T get(int index) {
        validateIndex(index);

        Node<T> current = this.head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        return current.getData();
    }

    /**
     * Removes the element at the specified position in this list.
     *
     * @param index The index of the element to be removed.
     * @return The element previously at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    @Override
    public T remove(int index) {
        validateIndex(index);

        if (index == 0) {
            return removeFirst();
        }

        Node<T> current = this.head;
        for (int i = 0; i < index - 1; i++) {
            current = current.getNext();
        }

        T data = current.getNext().getData();
        current.setNext(current.getNext().getNext());

        if (current.getNext() == null) {
            this.tail = current;
        }

        this.size--;
        log.debug("Elemento removido en índice {}. Tamaño actual: {}", index, this.size);

        return data;
    }

    /**
     * Removes and returns the first element from this list.
     *
     * @return The first element.
     * @throws NoSuchElementException if the list is empty.
     */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("La lista está vacía");
        }

        T data = this.head.getData();
        this.head = this.head.getNext();

        if (this.head == null) {
            this.tail = null;
        }

        this.size--;
        log.debug("Primer elemento removido. Tamaño actual: {}", this.size);

        return data;
    }

    /**
     * Removes and returns the last element from this list.
     *
     * @return The last element.
     * @throws NoSuchElementException if the list is empty.
     */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("La lista está vacía");
        }

        if (this.size == 1) {
            return removeFirst();
        }

        Node<T> current = this.head;
        while (current.getNext() != this.tail) {
            current = current.getNext();
        }

        T data = this.tail.getData();
        current.setNext(null);
        this.tail = current;

        this.size--;
        log.debug("Último elemento removido. Tamaño actual: {}", this.size);

        return data;
    }

    /**
     * Returns true if this list contains the specified element.
     *
     * @param element The element whose presence is to be tested.
     * @return true if this list contains the specified element.
     */
    @Override
    public boolean contains(T element) {
        Node<T> current = this.head;

        while (current != null) {
            if (current.getData().equals(element)) {
                return true;
            }
            current = current.getNext();
        }

        return false;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return The size of the list.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Checks if the list is empty.
     *
     * @return true if the list contains no elements.
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Removes all elements from this list.
     */
    @Override
    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
        log.debug("Lista limpiada");
    }

    /**
     * Validates that the given index is within valid bounds.
     *
     * @param index The index to validate.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    private void validateIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(String.format("Índice %d fuera de rango [0, %d)", index, this.size));
        }
    }

    /**
     * Returns a string representation of the linked list.
     *
     * @return String representation like "[element1, element2]".
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        Node<T> current = this.head;

        while (current != null) {
            sb.append(current.getData());
            if (current.getNext() != null) {
                sb.append(", ");
            }
            current = current.getNext();
        }

        sb.append("]");
        return sb.toString();
    }
}
