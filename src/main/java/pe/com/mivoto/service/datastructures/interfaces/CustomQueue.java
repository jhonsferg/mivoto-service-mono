package pe.com.mivoto.service.datastructures.interfaces;

public interface CustomQueue<T> {
    void enqueue(T element);

    T dequeue();

    T peek();

    boolean isEmpty();

    int size();

    void clear();
}