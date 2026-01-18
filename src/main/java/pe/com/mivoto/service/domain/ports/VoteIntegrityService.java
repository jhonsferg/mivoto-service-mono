package pe.com.mivoto.service.domain.ports;

import java.util.List;

/**
 * Service responsible for ensuring the integrity and verifiability of votes.
 * Utilizes cryptographic proofs (e.g., Merkle Trees) to validate vote sets.
 */
public interface VoteIntegrityService {

    /**
     * Generates an integrity proof (e.g., Merkle Root) for a list of vote hashes.
     *
     * @param voteHashes A list of hashed vote data.
     * @return The root hash or proof string.
     */
    String generateIntegrityProof(List<String> voteHashes);

    /**
     * Verifies if a given set of vote hashes results in the expected root hash.
     *
     * @param voteHashes The list of vote hashes to verify.
     * @param rootHash   The expected root hash.
     * @return true if the hashes match the root, false otherwise.
     */
    boolean verifyIntegrity(List<String> voteHashes, String rootHash);
}
