package pe.com.mivoto.service.datastructures.interfaces;

import java.util.List;

public interface CustomTree<T extends Comparable<T>> {
    void insert(T value);

    boolean search(T value);

    void delete(T value);

    List<T> inorderTraversal();

    List<T> preorderTraversal();

    List<T> postorderTraversal();

    int height();

    int size();

    boolean isEmpty();

    T findMin();

    T findMax();
}
