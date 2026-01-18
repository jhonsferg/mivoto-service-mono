package pe.com.mivoto.service.datastructures.nonlinear.graph;

import java.util.*;

public class GraphAlgorithms {

    /**
     * Performs a topological sort on a directed acyclic graph (DAG).
     *
     * @param graph The graph to sort.
     * @param <T>   The type of vertex data.
     * @return A list of vertices in topological order.
     */
    public static <T> List<T> topologicalSort(Graph<T> graph) {
        List<T> result = new ArrayList<>();
        Set<T> visited = new HashSet<>();
        Stack<T> stack = new Stack<>();

        for (T vertex : getVertices(graph)) {
            if (!visited.contains(vertex)) {
                topologicalSortDFS(vertex, graph, visited, stack);
            }
        }

        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }

        return result;
    }

    private static <T> void topologicalSortDFS(T vertex, Graph<T> graph,
            Set<T> visited, Stack<T> stack) {
        visited.add(vertex);

        for (T neighbor : graph.getNeighbors(vertex)) {
            if (!visited.contains(neighbor)) {
                topologicalSortDFS(neighbor, graph, visited, stack);
            }
        }

        stack.push(vertex);
    }

    /**
     * Finds all connected components in an undirected graph.
     *
     * @param graph The graph to search.
     * @param <T>   The type of vertex data.
     * @return A list of sets, where each set represents a connected component.
     */
    public static <T> List<Set<T>> findConnectedComponents(Graph<T> graph) {
        List<Set<T>> components = new ArrayList<>();
        Set<T> visited = new HashSet<>();

        for (T vertex : getVertices(graph)) {
            if (!visited.contains(vertex)) {
                Set<T> component = new HashSet<>();
                dfsComponent(vertex, graph, visited, component);
                components.add(component);
            }
        }

        return components;
    }

    private static <T> void dfsComponent(T vertex, Graph<T> graph,
            Set<T> visited, Set<T> component) {
        visited.add(vertex);
        component.add(vertex);

        for (T neighbor : graph.getNeighbors(vertex)) {
            if (!visited.contains(neighbor)) {
                dfsComponent(neighbor, graph, visited, component);
            }
        }
    }

    /**
     * Checks if a graph is bipartite (2-colorable).
     *
     * @param graph The graph to check.
     * @param <T>   The type of vertex data.
     * @return true if the graph is bipartite, false otherwise.
     */
    public static <T> boolean isBipartite(Graph<T> graph) {
        Map<T, Integer> colors = new HashMap<>();

        for (T vertex : getVertices(graph)) {
            if (!colors.containsKey(vertex)) {
                if (!isBipartiteBFS(vertex, graph, colors)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static <T> boolean isBipartiteBFS(T start, Graph<T> graph, Map<T, Integer> colors) {
        Queue<T> queue = new LinkedList<>();
        colors.put(start, 0);
        queue.offer(start);

        while (!queue.isEmpty()) {
            T current = queue.poll();
            int currentColor = colors.get(current);

            for (T neighbor : graph.getNeighbors(current)) {
                if (!colors.containsKey(neighbor)) {
                    colors.put(neighbor, 1 - currentColor);
                    queue.offer(neighbor);
                } else if (colors.get(neighbor) == currentColor) {
                    return false;
                }
            }
        }

        return true;
    }

    private static <T> Set<T> getVertices(Graph<T> graph) {
        return graph.getAllVertices();
    }
}
