package pe.com.mivoto.service.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.com.mivoto.service.datastructures.nonlinear.tree.MerkleTree;
import pe.com.mivoto.service.domain.ports.VoteIntegrityService;

import java.util.List;

@Slf4j
@Service
public class MerkleVoteIntegrityService implements VoteIntegrityService {

    @Override
    public String generateIntegrityProof(List<String> voteHashes) {
        log.info("Generating integrity proof for {} votes", voteHashes.size());
        MerkleTree merkleTree = new MerkleTree(voteHashes);
        String root = merkleTree.getRoot();
        log.info("Integrity proof (Root Hash): {}", root);
        return root;
    }

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
