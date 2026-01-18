package pe.com.mivoto.service.datastructures.nonlinear.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pe.com.mivoto.service.datastructures.interfaces.CustomGraph;

import java.util.*;

/**
 * Generic implementation of a Graph data structure.
 * Supports both directed and undirected graphs, with weighted edges.
 *
 * @param <T> The type of data stored in the graph vertices. Must implement
 *            equals/hashCode correctly.
 */
@Slf4j
public class Graph<T> implements CustomGraph<T> {

    private final Map<T, GraphNode<T>> vertices;
    private final boolean isDirected;
    private int edgeCount;

    /**
     * Constructs a new Graph.
     *
     * @param isDirected true if the graph is directed, false otherwise.
     */
    public Graph(boolean isDirected) {
        this.vertices = new HashMap<>();
        this.isDirected = isDirected;
        this.edgeCount = 0;
    }

    /**
     * Retrieves all vertices currently in the graph.
     *
     * @return A Set containing all vertices.
     */
    public Set<T> getAllVertices() {
        return vertices.keySet();
    }

    /**
     * Adds a vertex to the graph.
     * If the vertex already exists, this method does nothing.
     *
     * @param vertex The vertex to add.
     */
    @Override
    public void addVertex(T vertex) {
        if (!vertices.containsKey(vertex)) {
            vertices.put(vertex, new GraphNode<>(vertex));
            log.debug("Vértice agregado: {}. Total vértices: {}", vertex, vertices.size());
        }
    }

    /**
     * Adds an edge between two vertices with a specified weight.
     * If the vertices do not exist, they are added automatically.
     * If the graph is undirected, the edge is added in both directions.
     *
     * @param from   The source vertex.
     * @param to     The destination vertex.
     * @param weight The weight of the edge.
     */
    @Override
    public void addEdge(T from, T to, int weight) {
        addVertex(from);
        addVertex(to);

        GraphNode<T> fromNode = vertices.get(from);

        boolean edgeExists = fromNode.getEdges().stream().anyMatch(edge -> edge.getDestination().equals(to));

        if (!edgeExists) {
            fromNode.addEdge(new Edge<>(to, weight));
            edgeCount++;

            if (!isDirected) {
                GraphNode<T> toNode = vertices.get(to);
                toNode.addEdge(new Edge<>(from, weight));
            }

            log.debug("Arista agregada: {} -> {} (peso: {})", from, to, weight);
        }
    }

    /**
     * Removes a vertex and all connected edges from the graph.
     *
     * @param vertex The vertex to remove.
     */
    @Override
    public void removeVertex(T vertex) {
        if (!vertices.containsKey(vertex)) {
            return;
        }

        for (GraphNode<T> node : vertices.values()) {
            node.getEdges().removeIf(edge -> edge.getDestination().equals(vertex));
        }

        edgeCount -= vertices.get(vertex).getEdges().size();
        vertices.remove(vertex);

        log.debug("Vértice removido: {}. Total vértices: {}", vertex, vertices.size());
    }

    /**
     * Removes an edge between two vertices.
     * If the graph is undirected, the edge is removed in both directions.
     *
     * @param from The source vertex.
     * @param to   The destination vertex.
     */
    @Override
    public void removeEdge(T from, T to) {
        if (!vertices.containsKey(from)) {
            return;
        }

        GraphNode<T> fromNode = vertices.get(from);
        boolean removed = fromNode.getEdges().removeIf(
                edge -> edge.getDestination().equals(to));

        if (removed) {
            edgeCount--;

            if (!isDirected && vertices.containsKey(to)) {
                GraphNode<T> toNode = vertices.get(to);
                toNode.getEdges().removeIf(edge -> edge.getDestination().equals(from));
            }

            log.debug("Arista removida: {} -> {}", from, to);
        }
    }

    /**
     * Checks if a vertex exists in the graph.
     *
     * @param vertex The vertex to check.
     * @return true if the vertex exists, false otherwise.
     */
    @Override
    public boolean hasVertex(T vertex) {
        return vertices.containsKey(vertex);
    }

    /**
     * Checks if an edge exists between two vertices.
     *
     * @param from The source vertex.
     * @param to   The destination vertex.
     * @return true if the edge exists, false otherwise.
     */
    @Override
    public boolean hasEdge(T from, T to) {
        if (!vertices.containsKey(from)) {
            return false;
        }

        GraphNode<T> fromNode = vertices.get(from);
        return fromNode.getEdges().stream()
                .anyMatch(edge -> edge.getDestination().equals(to));
    }

    /**
     * Retrieves the neighbors of a given vertex.
     *
     * @param vertex The vertex to find neighbors for.
     * @return A list of neighboring vertices. Returns an empty list if the vertex
     *         does not exist.
     */
    @Override
    public List<T> getNeighbors(T vertex) {
        if (!vertices.containsKey(vertex)) {
            return new ArrayList<>();
        }

        GraphNode<T> node = vertices.get(vertex);
        List<T> neighbors = new ArrayList<>();

        for (Edge<T> edge : node.getEdges()) {
            neighbors.add(edge.getDestination());
        }

        return neighbors;
    }

    /**
     * Returns the total number of vertices in the graph.
     *
     * @return The vertex count.
     */
    @Override
    public int getVertexCount() {
        return vertices.size();
    }

    /**
     * Returns the total number of edges in the graph.
     *
     * @return The edge count.
     */
    @Override
    public int getEdgeCount() {
        return edgeCount;
    }

    /**
     * Performs a Depth-First Search (DFS) starting from a given vertex.
     *
     * @param start The starting vertex.
     * @return A list of vertices in the order they were visited.
     */
    @Override
    public List<T> depthFirstSearch(T start) {
        if (!vertices.containsKey(start)) {
            return new ArrayList<>();
        }

        List<T> result = new ArrayList<>();
        Set<T> visited = new HashSet<>();
        dfsRecursive(start, visited, result);

        return result;
    }

    /**
     * Recursive helper for DFS traversal.
     *
     * @param vertex  The current vertex.
     * @param visited Set of visited vertices.
     * @param result  List to accumulate traversal order.
     */
    private void dfsRecursive(T vertex, Set<T> visited, List<T> result) {
        visited.add(vertex);
        result.add(vertex);

        GraphNode<T> node = vertices.get(vertex);
        for (Edge<T> edge : node.getEdges()) {
            T neighbor = edge.getDestination();
            if (!visited.contains(neighbor)) {
                dfsRecursive(neighbor, visited, result);
            }
        }
    }

    /**
     * Performs a Breadth-First Search (BFS) starting from a given vertex.
     *
     * @param start The starting vertex.
     * @return A list of vertices in the order they were visited.
     */
    @Override
    public List<T> breadthFirstSearch(T start) {
        if (!vertices.containsKey(start)) {
            return new ArrayList<>();
        }

        List<T> result = new ArrayList<>();
        Set<T> visited = new HashSet<>();
        Queue<T> queue = new LinkedList<>();

        visited.add(start);
        queue.offer(start);

        while (!queue.isEmpty()) {
            T current = queue.poll();
            result.add(current);

            GraphNode<T> node = vertices.get(current);
            for (Edge<T> edge : node.getEdges()) {
                T neighbor = edge.getDestination();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }

        return result;
    }

    /**
     * Calculates the shortest path from a start vertex to all other accessible
     * vertices using Dijkstra's algorithm.
     * Assumes non-negative edge weights.
     *
     * @param start The starting vertex.
     * @return A map where the key is the destination vertex and the value is the
     *         minimum distance from the start vertex.
     */
    @Override
    public Map<T, Integer> shortestPath(T start) {
        if (!vertices.containsKey(start)) {
            return new HashMap<>();
        }

        Map<T, Integer> distances = new HashMap<>();
        PriorityQueue<NodeDistance<T>> pq = new PriorityQueue<>(Comparator.comparingInt(NodeDistance::getDistance));
        Set<T> visited = new HashSet<>();

        for (T vertex : vertices.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(start, 0);

        pq.offer(new NodeDistance<>(start, 0));

        while (!pq.isEmpty()) {
            NodeDistance<T> current = pq.poll();
            T currentVertex = current.getNode();

            if (visited.contains(currentVertex)) {
                continue;
            }

            visited.add(currentVertex);

            GraphNode<T> node = vertices.get(currentVertex);
            for (Edge<T> edge : node.getEdges()) {
                T neighbor = edge.getDestination();
                int newDistance = distances.get(currentVertex) + edge.getWeight();

                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    pq.offer(new NodeDistance<>(neighbor, newDistance));
                }
            }
        }

        return distances;
    }

    /**
     * Detects if the graph contains any cycles.
     * Uses DFS with a recursion stack tracking for directed graphs.
     *
     * @return true if a cycle is detected, false otherwise.
     */
    public boolean hasCycle() {
        Set<T> visited = new HashSet<>();
        Set<T> recursionStack = new HashSet<>();

        for (T vertex : vertices.keySet()) {
            if (!visited.contains(vertex)) {
                if (hasCycleDFS(vertex, visited, recursionStack)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Recursive helper for cycle detection using DFS.
     *
     * @param vertex         The current vertex.
     * @param visited        Set of all visited vertices.
     * @param recursionStack Set of vertices in the current recursion stack (for
     *                       directed cycle check).
     * @return true if cycle found.
     */
    private boolean hasCycleDFS(T vertex, Set<T> visited, Set<T> recursionStack) {
        visited.add(vertex);
        recursionStack.add(vertex);

        GraphNode<T> node = vertices.get(vertex);
        for (Edge<T> edge : node.getEdges()) {
            T neighbor = edge.getDestination();

            if (!visited.contains(neighbor)) {
                if (hasCycleDFS(neighbor, visited, recursionStack)) {
                    return true;
                }
            } else if (recursionStack.contains(neighbor)) {
                return true;
            }
        }

        recursionStack.remove(vertex);
        return false;
    }

    /**
     * Returns a string representation of the graph adjacency list.
     *
     * @return String representation of vertices and edges.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Grafo ").append(isDirected ? "Dirigido" : "No Dirigido").append(":\n");

        for (Map.Entry<T, GraphNode<T>> entry : vertices.entrySet()) {
            sb.append(entry.getKey()).append(" -> [");

            List<Edge<T>> edges = entry.getValue().getEdges();
            for (int i = 0; i < edges.size(); i++) {
                Edge<T> edge = edges.get(i);
                sb.append(edge.getDestination());
                if (edge.getWeight() != 1) {
                    sb.append("(").append(edge.getWeight()).append(")");
                }
                if (i < edges.size() - 1) {
                    sb.append(", ");
                }
            }

            sb.append("]\n");
        }

        return sb.toString();
    }

    @Data
    @AllArgsConstructor
    private static class NodeDistance<T> {
        private T node;
        private int distance;
    }
}
