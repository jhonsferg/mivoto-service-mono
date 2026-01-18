package pe.com.mivoto.service.datastructures.nonlinear.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an edge in a graph data structure.
 * An edge connects one vertex to another and has an associated weight.
 *
 * @param <T> The type of vertex this edge points to.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edge<T> {
    /**
     * The destination vertex of this edge.
     */
    private T destination;

    /**
     * The weight/cost of this edge. Default is 1.
     */
    private int weight;

    /**
     * Constructs an edge with a destination and default weight of 1.
     *
     * @param destination The destination vertex.
     */
    public Edge(T destination) {
        this.destination = destination;
        this.weight = 1;
    }
}
