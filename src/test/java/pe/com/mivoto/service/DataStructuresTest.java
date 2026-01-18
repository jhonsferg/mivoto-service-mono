package pe.com.mivoto.service;

import org.junit.jupiter.api.Test;
import pe.com.mivoto.service.datastructures.nonlinear.graph.Graph;
import pe.com.mivoto.service.datastructures.nonlinear.graph.GraphAlgorithms;
import pe.com.mivoto.service.datastructures.nonlinear.tree.AVLTree;
import pe.com.mivoto.service.datastructures.nonlinear.tree.MerkleTree;
import pe.com.mivoto.service.datastructures.nonlinear.tree.TreeTraversal;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DataStructuresTest {

    @Test
    void testAVLTree() {
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);
        tree.insert(25);

        // Check balancing (root should be 30 for this sequence)
        assertEquals(30, tree.getRoot().getValue());
        assertTrue(tree.search(25));

        List<Integer> inOrder = TreeTraversal.inOrder(tree.getRoot());
        assertEquals(Arrays.asList(10, 20, 25, 30, 40, 50), inOrder);
    }

    @Test
    void testMerkleTree() {
        List<String> transactions = Arrays.asList("Vote1", "Vote2", "Vote3", "Vote4");
        MerkleTree merkleTree = new MerkleTree(transactions);

        assertNotNull(merkleTree.getRoot());
        assertFalse(merkleTree.getRoot().isEmpty());

        // Ensure determinism
        MerkleTree merkleTree2 = new MerkleTree(transactions);
        assertEquals(merkleTree.getRoot(), merkleTree2.getRoot());

        // Ensure sensitivity
        List<String> tampered = Arrays.asList("Vote1", "Vote2", "Vote3", "Vote5");
        MerkleTree tamperedTree = new MerkleTree(tampered);
        assertNotEquals(merkleTree.getRoot(), tamperedTree.getRoot());
    }

    @Test
    void testGraphAndAlgorithms() {
        Graph<String> graph = new Graph<>(false); // Undirected
        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 1);
        graph.addEdge("C", "A", 1); // Cycle

        assertTrue(graph.hasVertex("A"));
        assertTrue(graph.hasEdge("A", "B"));
        assertTrue(graph.hasCycle());

        Set<String> vertices = graph.getAllVertices();
        assertEquals(3, vertices.size());

        // Test GraphAlgorithms
        // We use findConnectedComponents which internally uses getVertices
        List<Set<String>> components = GraphAlgorithms.findConnectedComponents(graph);
        assertEquals(1, components.size());
        assertEquals(3, components.get(0).size());
    }
}
