package pe.com.mivoto.service.datastructures.nonlinear.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class providing static methods for standard binary tree traversals.
 */
public class TreeTraversal {

    /**
     * Performs an in-order traversal (Left -> Root -> Right).
     *
     * @param root The root of the tree/subtree.
     * @param <T>  The type of value in the tree.
     * @return A list of values in in-order sequence.
     */
    public static <T extends Comparable<T>> List<T> inOrder(TreeNode<T> root) {
        List<T> result = new ArrayList<>();
        inOrderRecursive(root, result);
        return result;
    }

    private static <T extends Comparable<T>> void inOrderRecursive(TreeNode<T> node, List<T> result) {
        if (node != null) {
            inOrderRecursive(node.getLeft(), result);
            result.add(node.getValue());
            inOrderRecursive(node.getRight(), result);
        }
    }

    /**
     * Performs a pre-order traversal (Root -> Left -> Right).
     *
     * @param root The root of the tree/subtree.
     * @param <T>  The type of value in the tree.
     * @return A list of values in pre-order sequence.
     */
    public static <T extends Comparable<T>> List<T> preOrder(TreeNode<T> root) {
        List<T> result = new ArrayList<>();
        preOrderRecursive(root, result);
        return result;
    }

    private static <T extends Comparable<T>> void preOrderRecursive(TreeNode<T> node, List<T> result) {
        if (node != null) {
            result.add(node.getValue());
            preOrderRecursive(node.getLeft(), result);
            preOrderRecursive(node.getRight(), result);
        }
    }

    /**
     * Performs a post-order traversal (Left -> Right -> Root).
     *
     * @param root The root of the tree/subtree.
     * @param <T>  The type of value in the tree.
     * @return A list of values in post-order sequence.
     */
    public static <T extends Comparable<T>> List<T> postOrder(TreeNode<T> root) {
        List<T> result = new ArrayList<>();
        postOrderRecursive(root, result);
        return result;
    }

    private static <T extends Comparable<T>> void postOrderRecursive(TreeNode<T> node, List<T> result) {
        if (node != null) {
            postOrderRecursive(node.getLeft(), result);
            postOrderRecursive(node.getRight(), result);
            result.add(node.getValue());
        }
    }
}
