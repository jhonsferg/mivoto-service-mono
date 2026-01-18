package pe.com.mivoto.service.datastructures.interfaces;

import java.util.List;

/**
 * Generic interface for custom list data structures.
 * Defines basic list operations for adding, removing, and accessing elements.
 *
 * @param <T> The type of elements stored in the list.
 */
public interface CustomList<T> {
    /**
     * Adds an element to the end of the list.
     *
     * @param element The element to add.
     */
    void add(T element);

    /**
     * Adds an element at the beginning of the list.
     *
     * @param element The element to add at the front.
     */
    void addFirst(T element);

    /**
     * Adds an element at the end of the list.
     *
     * @param element The element to add at the end.
     */
    void addLast(T element);

    /**
     * Retrieves the element at the specified index.
     *
     * @param index The index of the element to retrieve.
     * @return The element at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    T get(int index);

    /**
     * Removes and returns the element at the specified index.
     *
     * @param index The index of the element to remove.
     * @return The removed element.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    T remove(int index);

    /**
     * Removes and returns the first element from the list.
     *
     * @return The first element.
     * @throws IllegalStateException if the list is empty.
     */
    T removeFirst();

    /**
     * Removes and returns the last element from the list.
     *
     * @return The last element.
     * @throws IllegalStateException if the list is empty.
     */
    T removeLast();

    /**
     * Checks if the list contains the specified element.
     *
     * @param element The element to search for.
     * @return true if the element is found, false otherwise.
     */
    boolean contains(T element);

    /**
     * Returns the number of elements in the list.
     *
     * @return The size of the list.
     */
    int size();

    /**
     * Checks if the list is empty.
     *
     * @return true if the list contains no elements, false otherwise.
     */
    boolean isEmpty();

    /**
     * Removes all elements from the list.
     */
    void clear();
}
