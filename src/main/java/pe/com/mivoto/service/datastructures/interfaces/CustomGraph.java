package pe.com.mivoto.service.datastructures.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Generic interface for custom graph data structures.
 * Supports both directed and undirected graphs with weighted edges.
 *
 * @param <T> The type of vertex data stored in the graph.
 */
public interface CustomGraph<T> {
    /**
     * Adds a vertex to the graph.
     * If the vertex already exists, this operation has no effect.
     *
     * @param vertex The vertex to add.
     */
    void addVertex(T vertex);

    /**
     * Adds a weighted edge between two vertices.
     * For undirected graphs, the edge is bidirectional.
     *
     * @param from   The source vertex.
     * @param to     The destination vertex.
     * @param weight The weight of the edge.
     */
    void addEdge(T from, T to, int weight);

    /**
     * Removes a vertex and all its connected edges from the graph.
     *
     * @param vertex The vertex to remove.
     */
    void removeVertex(T vertex);

    /**
     * Removes the edge between two vertices.
     *
     * @param from The source vertex.
     * @param to   The destination vertex.
     */
    void removeEdge(T from, T to);

    /**
     * Checks if the graph contains a specific vertex.
     *
     * @param vertex The vertex to check.
     * @return true if the vertex exists, false otherwise.
     */
    boolean hasVertex(T vertex);

    /**
     * Checks if there is an edge between two vertices.
     *
     * @param from The source vertex.
     * @param to   The destination vertex.
     * @return true if the edge exists, false otherwise.
     */
    boolean hasEdge(T from, T to);

    /**
     * Retrieves all neighboring vertices of a given vertex.
     *
     * @param vertex The vertex to find neighbors for.
     * @return A list of adjacent vertices.
     */
    List<T> getNeighbors(T vertex);

    /**
     * Returns the total number of vertices in the graph.
     *
     * @return The vertex count.
     */
    int getVertexCount();

    /**
     * Returns the total number of edges in the graph.
     *
     * @return The edge count.
     */
    int getEdgeCount();

    /**
     * Performs a Depth-First Search traversal starting from a vertex.
     *
     * @param start The starting vertex.
     * @return A list of vertices in DFS order.
     */
    List<T> depthFirstSearch(T start);

    /**
     * Performs a Breadth-First Search traversal starting from a vertex.
     *
     * @param start The starting vertex.
     * @return A list of vertices in BFS order.
     */
    List<T> breadthFirstSearch(T start);

    /**
     * Calculates the shortest path from a start vertex to all other vertices.
     * Uses Dijkstra's algorithm for weighted graphs.
     *
     * @param start The starting vertex.
     * @return A map of vertices to their shortest distance from the start.
     */
    Map<T, Integer> shortestPath(T start);
}
