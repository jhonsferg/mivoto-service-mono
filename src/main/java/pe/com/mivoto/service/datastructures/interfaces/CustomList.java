package pe.com.mivoto.service.datastructures.interfaces;

public interface CustomList<T> {
    void add(T element);

    void addFirst(T element);

    void addLast(T element);

    T get(int index);

    T remove(int index);

    T removeFirst();

    T removeLast();

    boolean contains(T element);

    int size();

    boolean isEmpty();

    void clear();
}
