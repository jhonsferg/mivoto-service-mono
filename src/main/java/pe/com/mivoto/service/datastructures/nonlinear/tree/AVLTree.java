package pe.com.mivoto.service.datastructures.nonlinear.tree;

import pe.com.mivoto.service.datastructures.interfaces.CustomTree;

import java.util.List;

/**
 * Implementation of an AVL Tree (Adelson-Velsky and Landis).
 * This is a self-balancing binary search tree where the difference between
 * heights of left and right subtrees cannot be more than one for all nodes.
 *
 * @param <T> The type of data stored in the tree, must be Comparable.
 */
public class AVLTree<T extends Comparable<T>> implements CustomTree<T> {

    private TreeNode<T> root;
    private int size;

    /**
     * Constructs a new empty AVL Tree.
     */
    public AVLTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Inserts a value into the AVL tree.
     * The tree is automatically rebalanced after insertion if necessary.
     *
     * @param value The value to insert. Cannot be null.
     * @throws IllegalArgumentException if the value is null.
     */
    @Override
    public void insert(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        if (!search(value)) {
            root = insert(root, value);
            size++;
        }
    }

    /**
     * Recursive helper to insert a value into the subtree.
     *
     * @param node  The root of the subtree.
     * @param value The value to insert.
     * @return The new root of the subtree.
     */
    private TreeNode<T> insert(TreeNode<T> node, T value) {
        if (node == null) {
            return new TreeNode<>(value);
        }

        if (value.compareTo(node.getValue()) < 0) {
            node.setLeft(insert(node.getLeft(), value));
        } else if (value.compareTo(node.getValue()) > 0) {
            node.setRight(insert(node.getRight(), value));
        } else {
            return node;
        }

        node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));

        int balance = getBalance(node);

        if (balance > 1 && value.compareTo(node.getLeft().getValue()) < 0) {
            return rightRotate(node);
        }

        if (balance < -1 && value.compareTo(node.getRight().getValue()) > 0) {
            return leftRotate(node);
        }

        if (balance > 1 && value.compareTo(node.getLeft().getValue()) > 0) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }

        if (balance < -1 && value.compareTo(node.getRight().getValue()) < 0) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }

        return node;
    }

    /**
     * Deletes a value from the AVL tree.
     * The tree is automatically rebalanced after deletion if necessary.
     *
     * @param value The value to delete.
     */
    @Override
    public void delete(T value) {
        if (value != null && search(value)) {
            root = delete(root, value);
            size--;
        }
    }

    /**
     * Recursive helper to delete a value from the subtree.
     *
     * @param root  The root of the subtree.
     * @param value The value to delete.
     * @return The new root of the subtree.
     */
    private TreeNode<T> delete(TreeNode<T> root, T value) {
        if (root == null) {
            return root;
        }

        if (value.compareTo(root.getValue()) < 0) {
            root.setLeft(delete(root.getLeft(), value));
        } else if (value.compareTo(root.getValue()) > 0) {
            root.setRight(delete(root.getRight(), value));
        } else {
            if ((root.getLeft() == null) || (root.getRight() == null)) {
                TreeNode<T> temp = null;
                if (temp == root.getLeft()) {
                    temp = root.getRight();
                } else {
                    temp = root.getLeft();
                }

                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                TreeNode<T> temp = minValueNode(root.getRight());
                root.setValue(temp.getValue());
                root.setRight(delete(root.getRight(), temp.getValue()));
            }
        }

        if (root == null) {
            return root;
        }

        root.setHeight(Math.max(height(root.getLeft()), height(root.getRight())) + 1);

        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.getLeft()) >= 0) {
            return rightRotate(root);
        }

        if (balance > 1 && getBalance(root.getLeft()) < 0) {
            root.setLeft(leftRotate(root.getLeft()));
            return rightRotate(root);
        }

        if (balance < -1 && getBalance(root.getRight()) <= 0) {
            return leftRotate(root);
        }

        if (balance < -1 && getBalance(root.getRight()) > 0) {
            root.setRight(rightRotate(root.getRight()));
            return leftRotate(root);
        }

        return root;
    }

    /**
     * Searches for a value in the tree.
     *
     * @param value The value to search for.
     * @return true if the value exists, false otherwise.
     */
    @Override
    public boolean search(T value) {
        return searchRecursive(root, value) != null;
    }

    /**
     * Recursive helper to search for a value.
     *
     * @param node  The root of the subtree.
     * @param value The value to search for.
     * @return The node containing the value, or null if not found.
     */
    private TreeNode<T> searchRecursive(TreeNode<T> node, T value) {
        if (node == null || node.getValue().equals(value)) {
            return node;
        }
        if (value.compareTo(node.getValue()) < 0) {
            return searchRecursive(node.getLeft(), value);
        }
        return searchRecursive(node.getRight(), value);
    }

    /**
     * Checks if the tree contains the specified value (alias for search).
     *
     * @param value The value to check.
     * @return true if found, false otherwise.
     */
    public boolean contains(T value) {
        return search(value);
    }

    /**
     * Performs an in-order traversal of the tree.
     *
     * @return A list of values in ascending order.
     */
    @Override
    public List<T> inorderTraversal() {
        return TreeTraversal.inOrder(root);
    }

    /**
     * Performs a pre-order traversal of the tree.
     *
     * @return A list of values in pre-order.
     */
    @Override
    public List<T> preorderTraversal() {
        return TreeTraversal.preOrder(root);
    }

    /**
     * Performs a post-order traversal of the tree.
     *
     * @return A list of values in post-order.
     */
    @Override
    public List<T> postorderTraversal() {
        return TreeTraversal.postOrder(root);
    }

    /**
     * Returns the height of the tree.
     *
     * @return The height of the root node, or 0 if empty.
     */
    @Override
    public int height() {
        return height(root);
    }

    /**
     * Returns the number of elements in the tree.
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
     * @return true if the tree contains no elements, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears all elements from the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the minimum value in the tree.
     *
     * @return The minimum value, or null if the tree is empty.
     */
    @Override
    public T findMin() {
        if (isEmpty())
            return null;
        return minValueNode(root).getValue();
    }

    /**
     * Finds the maximum value in the tree.
     *
     * @return The maximum value, or null if the tree is empty.
     */
    @Override
    public T findMax() {
        if (isEmpty())
            return null;
        return maxValueNode(root).getValue();
    }

    /**
     * Retrieves the root node of the tree.
     *
     * @return The root TreeNode.
     */
    public TreeNode<T> getRoot() {
        return root;
    }

    /**
     * Helper to get the height of a node safely.
     *
     * @param node The node.
     * @return The height, or 0 if node is null.
     */
    private int height(TreeNode<T> node) {
        if (node == null) {
            return 0;
        }
        return node.getHeight();
    }

    /**
     * Calculates the balance factor of a node.
     *
     * @param node The node.
     * @return The balance factor (left height - right height).
     */
    private int getBalance(TreeNode<T> node) {
        if (node == null) {
            return 0;
        }
        return height(node.getLeft()) - height(node.getRight());
    }

    /**
     * Performs a right rotation on the subtree rooted at y.
     *
     * @param y The root of the subtree to rotate.
     * @return The new root of the subtree.
     */
    private TreeNode<T> rightRotate(TreeNode<T> y) {
        TreeNode<T> x = y.getLeft();
        TreeNode<T> T2 = x.getRight();

        x.setRight(y);
        y.setLeft(T2);

        y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) + 1);
        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);

        return x;
    }

    /**
     * Performs a left rotation on the subtree rooted at x.
     *
     * @param x The root of the subtree to rotate.
     * @return The new root of the subtree.
     */
    private TreeNode<T> leftRotate(TreeNode<T> x) {
        TreeNode<T> y = x.getRight();
        TreeNode<T> T2 = y.getLeft();

        y.setLeft(x);
        x.setRight(T2);

        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);
        y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) + 1);

        return y;
    }

    /**
     * Finds the node with the minimum value in a subtree.
     *
     * @param node The root of the subtree.
     * @return The node with the minimum value.
     */
    private TreeNode<T> minValueNode(TreeNode<T> node) {
        TreeNode<T> current = node;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    /**
     * Finds the node with the maximum value in a subtree.
     *
     * @param node The root of the subtree.
     * @return The node with the maximum value.
     */
    private TreeNode<T> maxValueNode(TreeNode<T> node) {
        TreeNode<T> current = node;
        while (current.getRight() != null) {
            current = current.getRight();
        }
        return current;
    }
}
