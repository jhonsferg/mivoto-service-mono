package pe.com.mivoto.service.datastructures.implementations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.datastructures.nonlinear.tree.BinarySearchTree;
import pe.com.mivoto.service.domain.model.Candidate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Specialized search tree for optimizing candidate lookups.
 * Wraps a {@link BinarySearchTree} to provide efficient operations by candidate
 * number.
 */
@Slf4j
@Component
public class CandidateSearchTree {
    private final BinarySearchTree<CandidateNode> tree;

    public CandidateSearchTree() {
        this.tree = new BinarySearchTree<>();
    }

    /**
     * Inserts a candidate into the search tree.
     *
     * @param candidate The candidate to insert.
     */
    public void insert(Candidate candidate) {
        CandidateNode node = new CandidateNode(candidate.getNumber(), candidate);
        this.tree.insert(node);
        log.debug("Candidato insertado - Número: {}, Nombre: {}", candidate.getNumber(), candidate.getName());
    }

    /**
     * Finds a candidate by their ballot number.
     *
     * @param number The candidate number to search for.
     * @return Optional containing the candidate if found.
     */
    public Optional<Candidate> findByNumber(Integer number) {
        CandidateNode searchNode = new CandidateNode(number, null);

        if (this.tree.search(searchNode)) {
            List<CandidateNode> nodes = this.tree.inorderTraversal();
            for (CandidateNode node : nodes) {
                if (node.getNumber().equals(number)) {
                    return Optional.of(node.getCandidate());
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Checks if a candidate with the specified number already exists in the tree.
     *
     * @param number The candidate number.
     * @return true if it exists.
     */
    public boolean existsByNumber(Integer number) {
        CandidateNode searchNode = new CandidateNode(number, null);
        return this.tree.search(searchNode);
    }

    /**
     * Deletes a candidate from the tree by their number.
     *
     * @param number The number of the candidate to remove.
     */
    public void delete(Integer number) {
        CandidateNode deleteNode = new CandidateNode(number, null);
        this.tree.delete(deleteNode);
        log.debug("Candidato eliminado - Número: {}", number);
    }

    /**
     * Retrieves all candidates in the tree ordered by their number (in-order
     * traversal).
     *
     * @return Ordered list of candidates.
     */
    public List<Candidate> getAllCandidatesOrdered() {
        List<CandidateNode> nodes = this.tree.inorderTraversal();
        List<Candidate> candidates = new ArrayList<>();

        for (CandidateNode node : nodes) {
            candidates.add(node.getCandidate());
        }

        return candidates;
    }

    public Optional<Candidate> findMinCandidate() {
        if (this.tree.isEmpty()) {
            return Optional.empty();
        }

        CandidateNode minNode = this.tree.findMin();
        return Optional.of(minNode.getCandidate());
    }

    public Optional<Candidate> findMaxCandidate() {
        if (this.tree.isEmpty()) {
            return Optional.empty();
        }

        CandidateNode maxNode = this.tree.findMax();
        return Optional.of(maxNode.getCandidate());
    }

    public List<Candidate> findInRange(Integer minNumber, Integer maxNumber) {
        List<CandidateNode> allNodes = this.tree.inorderTraversal();
        List<Candidate> result = new ArrayList<>();

        for (CandidateNode node : allNodes) {
            Integer number = node.getNumber();
            if (number >= minNumber && number <= maxNumber) {
                result.add(node.getCandidate());
            }
        }

        return result;
    }

    public int size() {
        return this.tree.size();
    }

    public boolean isEmpty() {
        return this.tree.isEmpty();
    }

    public int height() {
        return this.tree.height();
    }

    public boolean isBalanced() {
        return this.tree.isBalanced();
    }

    public void clear() {
        List<CandidateNode> nodes = this.tree.inorderTraversal();
        for (CandidateNode node : nodes) {
            this.tree.delete(node);
        }
        log.info("Árbol de candidatos limpiado");
    }

    public TreeStatistics getStatistics() {
        return new TreeStatistics(this.tree.size(), this.tree.height(), this.tree.isBalanced());
    }

    @Data
    @AllArgsConstructor
    public static class CandidateNode implements Comparable<CandidateNode> {
        private Integer number;
        private Candidate candidate;

        @Override
        public int compareTo(CandidateNode other) {
            return this.number.compareTo(other.number);
        }

        @Override
        public String toString() {
            return "CandidateNode{number=" + this.number + "}";
        }
    }

    @Data
    @AllArgsConstructor
    public static class TreeStatistics {
        private int totalCandidates;
        private int treeHeight;
        private boolean isBalanced;
    }
}
