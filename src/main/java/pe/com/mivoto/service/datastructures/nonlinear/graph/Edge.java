package pe.com.mivoto.service.datastructures.nonlinear.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edge<T> {
    private T destination;
    private int weight;

    public Edge(T destination) {
        this.destination = destination;
        this.weight = 1;
    }
}
