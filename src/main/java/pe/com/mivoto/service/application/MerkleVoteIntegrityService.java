package pe.com.mivoto.service.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.com.mivoto.service.datastructures.nonlinear.tree.MerkleTree;
import pe.com.mivoto.service.domain.ports.VoteIntegrityService;

import java.util.List;

/**
 * Implementation of the Vote Integrity Service using Merkle Trees.
 * Provides capabilities to generate proof of inclusion and verify vote
 * integrity
 * using SHA-256 hashing and Merkle Tree structures.
 */
@Slf4j
@Service
public class MerkleVoteIntegrityService implements VoteIntegrityService {

    /**
     * Generates a Merkle Root hash from a list of constituent vote hashes.
     * This root hash serves as a cryptographic commitment to the set of votes.
     *
     * @param voteHashes A list of individual vote SHA-256 hashes.
     * @return The hex-encoded Merkle Root hash.
     */
    @Override
    public String generateIntegrityProof(List<String> voteHashes) {
        log.info("Generating integrity proof for {} votes", voteHashes.size());
        MerkleTree merkleTree = new MerkleTree(voteHashes);
        String root = merkleTree.getRoot();
        log.info("Integrity proof (Root Hash): {}", root);
        return root;
    }

    /**
     * Verifies that a list of vote hashes produces the expected Merkle Root.
     * Use this method to audit the integrity of the vote storage.
     *
     * @param voteHashes The list of vote hashes to re-calculate the tree from.
     * @param rootHash   The expected root hash (previously generated).
     * @return true if the calculated root matches the provided rootHash, false
     *         otherwise.
     */
    @Override
    public boolean verifyIntegrity(List<String> voteHashes, String rootHash) {
        MerkleTree merkleTree = new MerkleTree(voteHashes);
        String calculatedRoot = merkleTree.getRoot();
        boolean matches = calculatedRoot.equals(rootHash);

        if (matches) {
            log.info("Integrity verification SUCCESS. Root matches.");
        } else {
            log.error("Integrity verification FAILED. Expected: {}, Calculated: {}", rootHash, calculatedRoot);
        }

        return matches;
    }
}
