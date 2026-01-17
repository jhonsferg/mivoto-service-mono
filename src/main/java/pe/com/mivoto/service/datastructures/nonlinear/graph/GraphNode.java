package pe.com.mivoto.service.datastructures.nonlinear.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphNode<T> {
    private T value;
    private List<Edge<T>> edges;

    public GraphNode(T value) {
        this.value = value;
        this.edges = new ArrayList<>();
    }

    public void addEdge(Edge<T> edge) {
        this.edges.add(edge);
    }
}