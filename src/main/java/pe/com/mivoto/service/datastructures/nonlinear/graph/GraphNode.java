package pe.com.mivoto.service.datastructures.nonlinear.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node (vertex) in a graph data structure.
 * Each node contains a value and a list of outgoing edges.
 *
 * @param <T> The type of data stored in the node.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphNode<T> {
    /**
     * The value/data stored in this node.
     */
    private T value;

    /**
     * List of outgoing edges from this node.
     */
    private List<Edge<T>> edges;

    /**
     * Constructs a graph node with a value and no edges.
     *
     * @param value The value to store in this node.
     */
    public GraphNode(T value) {
        this.value = value;
        this.edges = new ArrayList<>();
    }

    /**
     * Adds an edge to this node's list of outgoing edges.
     *
     * @param edge The edge to add.
     */
    public void addEdge(Edge<T> edge) {
        this.edges.add(edge);
    }
}