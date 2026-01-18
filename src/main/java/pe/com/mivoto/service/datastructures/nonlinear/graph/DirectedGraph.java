package pe.com.mivoto.service.datastructures.nonlinear.graph;

/**
 * Directed graph implementation extending the generic Graph class.
 * In a directed graph, edges have a direction from source to destination.
 * An edge from A to B does not imply an edge from B to A.
 *
 * @param <T> The type of data stored in vertices.
 */
public class DirectedGraph<T> extends Graph<T> {
    /**
     * Constructs a new directed graph.
     */
    public DirectedGraph() {
        super(true);
    }
}