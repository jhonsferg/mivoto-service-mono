package pe.com.mivoto.service.datastructures.nonlinear.tree;

import lombok.extern.slf4j.Slf4j;
import pe.com.mivoto.service.datastructures.interfaces.CustomTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom implementation of a Binary Search Tree (BST).
 * Maintains the BST property: for any node, all values in the left subtree
 * are less than the node's value, and all values in the right subtree are
 * greater.
 *
 * @param <T> The type of elements stored in the tree. Must be Comparable.
 */
@Slf4j
public class BinarySearchTree<T extends Comparable<T>> implements CustomTree<T> {

    private TreeNode<T> root;
    private int size;

    /**
     * Constructs an empty binary search tree.
     */
    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Inserts a value into the BST maintaining the search property.
     *
     * @param value The value to insert.
     */
    @Override
    public void insert(T value) {
        root = insertRecursive(root, value);
        size++;
        log.debug("Elemento insertado: {}. Tamaño actual: {}", value, size);
    }

    /**
     * Recursive helper to insert a value.
     *
     * @param node  The current node.
     * @param value The value to insert.
     * @return The updated node.
     */
    private TreeNode<T> insertRecursive(TreeNode<T> node, T value) {
        if (node == null) {
            return new TreeNode<>(value);
        }

        int comparison = value.compareTo(node.getValue());

        if (comparison < 0) {
            node.setLeft(insertRecursive(node.getLeft(), value));
        } else if (comparison > 0) {
            node.setRight(insertRecursive(node.getRight(), value));
        }

        return node;
    }

    /**
     * Searches for a value in the BST.
     *
     * @param value The value to search for.
     * @return true if the value exists, false otherwise.
     */
    @Override
    public boolean search(T value) {
        return searchRecursive(root, value);
    }

    /**
     * Recursive helper to search for a value.
     *
     * @param node  The current node.
     * @param value The value to search for.
     * @return true if found.
     */
    private boolean searchRecursive(TreeNode<T> node, T value) {
        if (node == null) {
            return false;
        }

        int comparison = value.compareTo(node.getValue());

        if (comparison == 0) {
            return true;
        } else if (comparison < 0) {
            return searchRecursive(node.getLeft(), value);
        } else {
            return searchRecursive(node.getRight(), value);
        }
    }

    /**
     * Deletes a value from the BST if it exists.
     *
     * @param value The value to delete.
     */
    @Override
    public void delete(T value) {
        if (search(value)) {
            root = deleteRecursive(root, value);
            size--;
            log.debug("Elemento eliminado: {}. Tamaño actual: {}", value, size);
        }
    }

    /**
     * Recursive helper to delete a value.
     *
     * @param node  The current node.
     * @param value The value to delete.
     * @return The updated node (potentially null or replaced).
     */
    private TreeNode<T> deleteRecursive(TreeNode<T> node, T value) {
        if (node == null) {
            return null;
        }

        int comparison = value.compareTo(node.getValue());

        if (comparison < 0) {
            node.setLeft(deleteRecursive(node.getLeft(), value));
        } else if (comparison > 0) {
            node.setRight(deleteRecursive(node.getRight(), value));
        } else {
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            }

            if (node.getLeft() == null) {
                return node.getRight();
            }
            if (node.getRight() == null) {
                return node.getLeft();
            }

            T successorValue = findMinRecursive(node.getRight());
            node.setValue(successorValue);
            node.setRight(deleteRecursive(node.getRight(), successorValue));
        }

        return node;
    }

    /**
     * Finds the minimum value in the BST.
     *
     * @return The minimum value.
     * @throws IllegalStateException if the tree is empty.
     */
    @Override
    public T findMin() {
        if (isEmpty()) {
            throw new IllegalStateException("El árbol está vacío");
        }
        return findMinRecursive(root);
    }

    /**
     * Recursive helper to find the minimum value.
     *
     * @param node The current node.
     * @return The minimum value found.
     */
    private T findMinRecursive(TreeNode<T> node) {
        if (node.getLeft() == null) {
            return node.getValue();
        }
        return findMinRecursive(node.getLeft());
    }

    /**
     * Finds the maximum value in the BST.
     *
     * @return The maximum value.
     * @throws IllegalStateException if the tree is empty.
     */
    @Override
    public T findMax() {
        if (isEmpty()) {
            throw new IllegalStateException("El árbol está vacío");
        }
        return findMaxRecursive(root);
    }

    /**
     * Recursive helper to find the maximum value.
     *
     * @param node The current node.
     * @return The maximum value found.
     */
    private T findMaxRecursive(TreeNode<T> node) {
        if (node.getRight() == null) {
            return node.getValue();
        }
        return findMaxRecursive(node.getRight());
    }

    /**
     * Performs an in-order traversal (Left-Root-Right).
     *
     * @return List of values in ascending order.
     */
    @Override
    public List<T> inorderTraversal() {
        List<T> result = new ArrayList<>();
        inorderRecursive(root, result);
        return result;
    }

    /**
     * Recursive helper for in-order traversal.
     *
     * @param node   The current node.
     * @param result List to accumulate values.
     */
    private void inorderRecursive(TreeNode<T> node, List<T> result) {
        if (node != null) {
            inorderRecursive(node.getLeft(), result);
            result.add(node.getValue());
            inorderRecursive(node.getRight(), result);
        }
    }

    /**
     * Performs a pre-order traversal (Root-Left-Right).
     *
     * @return List of values in pre-order.
     */
    @Override
    public List<T> preorderTraversal() {
        List<T> result = new ArrayList<>();
        preorderRecursive(root, result);
        return result;
    }

    /**
     * Recursive helper for pre-order traversal.
     *
     * @param node   The current node.
     * @param result List to accumulate values.
     */
    private void preorderRecursive(TreeNode<T> node, List<T> result) {
        if (node != null) {
            result.add(node.getValue());
            preorderRecursive(node.getLeft(), result);
            preorderRecursive(node.getRight(), result);
        }
    }

    /**
     * Performs a post-order traversal (Left-Right-Root).
     *
     * @return List of values in post-order.
     */
    @Override
    public List<T> postorderTraversal() {
        List<T> result = new ArrayList<>();
        postorderRecursive(root, result);
        return result;
    }

    /**
     * Recursive helper for post-order traversal.
     *
     * @param node   The current node.
     * @param result List to accumulate values.
     */
    private void postorderRecursive(TreeNode<T> node, List<T> result) {
        if (node != null) {
            postorderRecursive(node.getLeft(), result);
            postorderRecursive(node.getRight(), result);
            result.add(node.getValue());
        }
    }

    /**
     * Calculates the height of the tree.
     *
     * @return The height (max depth) of the tree.
     */
    @Override
    public int height() {
        return heightRecursive(root);
    }

    /**
     * Recursive helper to calculate height.
     *
     * @param node The current node.
     * @return The height of the subtree rooted at node.
     */
    private int heightRecursive(TreeNode<T> node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = heightRecursive(node.getLeft());
        int rightHeight = heightRecursive(node.getRight());

        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Returns the total number of nodes in the tree.
     *
     * @return The size of the tree.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checks if the tree is empty.
     *
     * @return true if the tree contains no nodes.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if the tree is height-balanced.
     * A tree is balanced if the height difference between left and right
     * subtrees is at most 1 for every node.
     *
     * @return true if the tree is balanced, false otherwise.
     */
    public boolean isBalanced() {
        return isBalancedRecursive(root);
    }

    /**
     * Recursive helper to check balance.
     *
     * @param node The current node.
     * @return true if the subtree rooted at node is balanced.
     */
    private boolean isBalancedRecursive(TreeNode<T> node) {
        if (node == null) {
            return true;
        }

        int leftHeight = heightRecursive(node.getLeft());
        int rightHeight = heightRecursive(node.getRight());

        if (Math.abs(leftHeight - rightHeight) > 1) {
            return false;
        }

        return isBalancedRecursive(node.getLeft()) &&
                isBalancedRecursive(node.getRight());
    }
}
