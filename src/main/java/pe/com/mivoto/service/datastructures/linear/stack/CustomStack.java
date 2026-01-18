package pe.com.mivoto.service.datastructures.linear.stack;

import lombok.extern.slf4j.Slf4j;

/**
 * Generic Stack implementation using a linked list structure.
 * Follows LIFO (Last-In-First-Out) principle.
 *
 * @param <T> The type of elements stored in the stack.
 */
@Slf4j
public class CustomStack<T> {
    private StackNode<T> top;
    private int size;

    /**
     * Constructs an empty stack.
     */
    public CustomStack() {
        this.top = null;
        this.size = 0;
    }

    /**
     * Pushes an element onto the top of the stack.
     *
     * @param element The element to push.
     */
    public void push(T element) {
        StackNode<T> newNode = new StackNode<>(element);
        newNode.setNext(top);
        top = newNode;
        size++;
        log.debug("Elemento agregado a la pila: {}. Tamaño: {}", element, size);
    }

    /**
     * Removes and returns the element at the top of the stack.
     *
     * @return The element at the top of the stack.
     * @throws IllegalStateException if the stack is empty.
     */
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("La pila está vacía");
        }

        T data = top.getData();
        top = top.getNext();
        size--;
        log.debug("Elemento removido de la pila: {}. Tamaño: {}", data, size);
        return data;
    }

    /**
     * Returns the element at the top of the stack without removing it.
     *
     * @return The element at the top of the stack.
     * @throws IllegalStateException if the stack is empty.
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("La pila está vacía");
        }
        return top.getData();
    }

    /**
     * Checks if the stack is empty.
     *
     * @return true if the stack is empty, false otherwise.
     */
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * Returns the number of elements in the stack.
     *
     * @return The size of the stack.
     */
    public int size() {
        return size;
    }

    /**
     * Removes all elements from the stack.
     */
    public void clear() {
        top = null;
        size = 0;
        log.debug("Pila limpiada");
    }

    /**
     * Returns a string representation of the stack.
     *
     * @return String representation of the stack from top to bottom.
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "Pila vacía";
        }

        StringBuilder sb = new StringBuilder("Pila: [");
        StackNode<T> current = top;
        while (current != null) {
            sb.append(current.getData());
            if (current.getNext() != null) {
                sb.append(" <- ");
            }
            current = current.getNext();
        }
        sb.append("]");
        return sb.toString();
    }
}
