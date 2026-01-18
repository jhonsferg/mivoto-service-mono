package pe.com.mivoto.service.datastructures.interfaces;

import java.util.List;

/**
 * Generic interface for custom binary search tree data structures.
 * Defines operations for tree-based data organization and retrieval.
 *
 * @param <T> The type of elements stored in the tree. Must be Comparable.
 */
public interface CustomTree<T extends Comparable<T>> {
    /**
     * Inserts a value into the tree.
     * If the value already exists, behavior depends on the implementation.
     *
     * @param value The value to insert.
     */
    void insert(T value);

    /**
     * Searches for a value in the tree.
     *
     * @param value The value to search for.
     * @return true if the value is found, false otherwise.
     */
    boolean search(T value);

    /**
     * Deletes a value from the tree.
     * If the value doesn't exist, this method may do nothing.
     *
     * @param value The value to delete.
     */
    void delete(T value);

    /**
     * Performs an inorder traversal of the tree.
     * Returns elements in sorted ascending order.
     *
     * @return A list of elements in inorder sequence.
     */
    List<T> inorderTraversal();

    /**
     * Performs a preorder traversal of the tree.
     * Visits root before children.
     *
     * @return A list of elements in preorder sequence.
     */
    List<T> preorderTraversal();

    /**
     * Performs a postorder traversal of the tree.
     * Visits children before root.
     *
     * @return A list of elements in postorder sequence.
     */
    List<T> postorderTraversal();

    /**
     * Returns the height of the tree.
     * An empty tree has height -1, a tree with one node has height 0.
     *
     * @return The height of the tree.
     */
    int height();

    /**
     * Returns the number of nodes in the tree.
     *
     * @return The size of the tree.
     */
    int size();

    /**
     * Checks if the tree is empty.
     *
     * @return true if the tree contains no elements, false otherwise.
     */
    boolean isEmpty();

    /**
     * Finds the minimum value in the tree.
     *
     * @return The minimum value.
     * @throws IllegalStateException if the tree is empty.
     */
    T findMin();

    /**
     * Finds the maximum value in the tree.
     *
     * @return The maximum value.
     * @throws IllegalStateException if the tree is empty.
     */
    T findMax();
}
