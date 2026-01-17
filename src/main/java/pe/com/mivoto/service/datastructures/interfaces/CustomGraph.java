package pe.com.mivoto.service.datastructures.interfaces;

import java.util.List;
import java.util.Map;

public interface CustomGraph<T> {
    void addVertex(T vertex);

    void addEdge(T from, T to, int weight);

    void removeVertex(T vertex);

    void removeEdge(T from, T to);

    boolean hasVertex(T vertex);

    boolean hasEdge(T from, T to);

    List<T> getNeighbors(T vertex);

    int getVertexCount();

    int getEdgeCount();

    List<T> depthFirstSearch(T start);

    List<T> breadthFirstSearch(T start);

    Map<T, Integer> shortestPath(T start);
}
