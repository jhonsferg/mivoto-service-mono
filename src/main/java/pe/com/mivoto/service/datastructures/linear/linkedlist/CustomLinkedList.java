package pe.com.mivoto.service.datastructures.linear.linkedlist;

import lombok.extern.slf4j.Slf4j;
import pe.com.mivoto.service.datastructures.interfaces.CustomList;

import java.util.NoSuchElementException;

@Slf4j
public class CustomLinkedList<T> implements CustomList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public CustomLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void add(T element) {
        addLast(element);
    }

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

    @Override
    public T get(int index) {
        validateIndex(index);

        Node<T> current = this.head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        return current.getData();
    }

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

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
        log.debug("Lista limpiada");
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(String.format("Índice %d fuera de rango [0, %d)", index, this.size));
        }
    }

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
