package pe.com.mivoto.service.datastructures.implementations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.datastructures.nonlinear.graph.DirectedGraph;
import pe.com.mivoto.service.domain.model.Election;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ElectionGraph {
    private final DirectedGraph<ElectionNode> graph;
    private final Map<Long, ElectionNode> electionMap;

    public ElectionGraph() {
        this.graph = new DirectedGraph<>();
        this.electionMap = new HashMap<>();
    }

    public void addElection(Election election, ElectionLevel level) {
        ElectionNode node = new ElectionNode(election.getId(), election.getTitle(), level, election);
        this.electionMap.put(election.getId(), node);
        this.graph.addVertex(node);

        log.debug("Elección agregada al grafo - ID: {}, Nivel: {}", election.getId(), level);
    }

    public void addRelationship(Long parentElectionId, Long childElectionId, RelationshipType type) {
        ElectionNode parent = this.electionMap.get(parentElectionId);
        ElectionNode child = this.electionMap.get(childElectionId);

        if (parent == null || child == null) {
            log.warn("No se puede crear relación - Elecciones no encontradas");
            return;
        }

        int weight = calculateRelationshipWeight(type);
        this.graph.addEdge(parent, child, weight);

        log.debug("Relación creada: {} -> {} (tipo: {}, peso: {})", parent.getTitle(), child.getTitle(), type, weight);
    }

    public List<Election> getSubElections(Long electionId) {
        ElectionNode node = this.electionMap.get(electionId);
        if (node == null) {
            return new ArrayList<>();
        }

        List<ElectionNode> neighbors = this.graph.getNeighbors(node);
        return neighbors.stream()
                .map(ElectionNode::getElection)
                .collect(Collectors.toList());
    }

    public ElectionHierarchy getHierarchy(Long rootElectionId) {
        ElectionNode root = this.electionMap.get(rootElectionId);
        if (root == null) {
            return null;
        }

        List<ElectionNode> hierarchy = this.graph.depthFirstSearch(root);

        return new ElectionHierarchy(
                root.getElection(),
                hierarchy.stream()
                        .map(ElectionNode::getElection)
                        .collect(Collectors.toList())
        );
    }

    public List<Election> findPath(Long fromElectionId, Long toElectionId) {
        ElectionNode from = this.electionMap.get(fromElectionId);
        ElectionNode to = this.electionMap.get(toElectionId);

        if (from == null || to == null) {
            return new ArrayList<>();
        }

        Map<ElectionNode, Integer> distances = this.graph.shortestPath(from);

        if (distances.get(to) == Integer.MAX_VALUE) {
            return new ArrayList<>();
        }

        List<Election> path = new ArrayList<>();
        path.add(from.getElection());
        path.add(to.getElection());

        return path;
    }

    public List<Election> getElectionsByLevel(ElectionLevel level) {
        return this.electionMap.values().stream()
                .filter(node -> node.getLevel() == level)
                .map(ElectionNode::getElection)
                .collect(Collectors.toList());
    }

    public RelatedElections getRelatedElections(Long electionId) {
        ElectionNode node = this.electionMap.get(electionId);
        if (node == null) {
            return null;
        }

        List<Election> children = getSubElections(electionId);
        List<Election> parents = new ArrayList<>();
        for (ElectionNode potential : this.electionMap.values()) {
            if (this.graph.hasEdge(potential, node)) {
                parents.add(potential.getElection());
            }
        }

        return new RelatedElections(parents, children);
    }

    public boolean hasRelationship(Long fromId, Long toId) {
        ElectionNode from = this.electionMap.get(fromId);
        ElectionNode to = this.electionMap.get(toId);

        if (from == null || to == null) {
            return false;
        }

        return this.graph.hasEdge(from, to);
    }

    public void removeElection(Long electionId) {
        ElectionNode node = this.electionMap.get(electionId);
        if (node != null) {
            this.graph.removeVertex(node);
            this.electionMap.remove(electionId);
            log.debug("Elección removida del grafo - ID: {}", electionId);
        }
    }

    public List<Election> getRootElections() {
        List<Election> roots = new ArrayList<>();

        for (ElectionNode node : this.electionMap.values()) {
            boolean hasParent = false;
            for (ElectionNode potential : this.electionMap.values()) {
                if (this.graph.hasEdge(potential, node)) {
                    hasParent = true;
                    break;
                }
            }

            if (!hasParent) {
                roots.add(node.getElection());
            }
        }

        return roots;
    }

    public boolean hasCycle() {
        return this.graph.hasCycle();
    }

    public GraphStatistics getStatistics() {
        return new GraphStatistics(this.graph.getVertexCount(), this.graph.getEdgeCount(), getRootElections().size(), hasCycle());
    }

    private int calculateRelationshipWeight(RelationshipType type) {
        return switch (type) {
            case HIERARCHICAL_STRONG -> 10;
            case HIERARCHICAL_MEDIUM -> 7;
            case HIERARCHICAL_WEAK -> 5;
            case DEPENDENCY -> 8;
            case INFLUENCE -> 6;
            case SEQUENTIAL -> 9;
        };
    }

    public enum ElectionLevel {
        NATIONAL,
        REGIONAL,
        LOCAL,
        DISTRICT,
        COMMUNITY
    }

    public enum RelationshipType {
        HIERARCHICAL_STRONG,
        HIERARCHICAL_MEDIUM,
        HIERARCHICAL_WEAK,
        DEPENDENCY,
        INFLUENCE,
        SEQUENTIAL
    }

    @Data
    @AllArgsConstructor
    public static class ElectionNode implements Comparable<ElectionNode> {
        private Long id;
        private String title;
        private ElectionLevel level;
        private Election election;

        @Override
        public int compareTo(ElectionNode other) {
            return this.id.compareTo(other.id);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ElectionNode that = (ElectionNode) o;
            return Objects.equals(this.id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.id);
        }

        @Override
        public String toString() {
            return "ElectionNode{id=" + this.id + ", title='" + this.title + "'}";
        }
    }

    @Data
    @AllArgsConstructor
    public static class ElectionHierarchy {
        private Election root;
        private List<Election> allElections;
    }

    @Data
    @AllArgsConstructor
    public static class RelatedElections {
        private List<Election> parents;
        private List<Election> children;
    }

    @Data
    @AllArgsConstructor
    public static class GraphStatistics {
        private int totalElections;
        private int totalRelationships;
        private int rootElections;
        private boolean hasCycles;
    }
}
