package pe.com.mivoto.service.datastructures.nonlinear.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeTraversal {

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
