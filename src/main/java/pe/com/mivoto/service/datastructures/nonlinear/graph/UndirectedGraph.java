package pe.com.mivoto.service.datastructures.nonlinear.graph;

/**
 * Undirected graph implementation extending the generic Graph class.
 * In an undirected graph, edges are bidirectional.
 * An edge between A and B allows traversal from both A to B and B to A.
 *
 * @param <T> The type of data stored in vertices.
 */
public class UndirectedGraph<T> extends Graph<T> {
    /**
     * Constructs a new undirected graph.
     */
    public UndirectedGraph() {
        super(false);
    }
}