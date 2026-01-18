package pe.com.mivoto.service.datastructures.nonlinear.tree;

import lombok.Data;

/**
 * Represents a node in the AVL Tree.
 * Stores a value, references to left and right children, and the height of the
 * node for balancing.
 *
 * @param <T> The type of value stored in the node.
 */
@Data
public class TreeNode<T extends Comparable<T>> {
    private T value;
    private TreeNode<T> left;
    private TreeNode<T> right;
    private int height;

    /**
     * Constructs a new TreeNode with the given value.
     * Height is initialized to 1.
     *
     * @param value The value to store in the node.
     */
    public TreeNode(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}