package pe.com.mivoto.service.datastructures.nonlinear.tree;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a Merkle Tree used for efficient data verification and
 * integrity checks.
 * In the context of the voting system, it is used to verify the integrity of
 * the vote blocks.
 */
public class MerkleTree {
    private List<String> transactions;
    private List<String> tree;
    private String root;

    /**
     * Constructs a Merkle Tree from a list of transaction strings (hashes).
     *
     * @param transactions A list of transaction data (typically vote hashes).
     */
    public MerkleTree(List<String> transactions) {
        this.transactions = new ArrayList<>(transactions);
        this.tree = new ArrayList<>();
        this.root = buildTree();
    }

    /**
     * Builds the Merkle Tree by recursively hashing pairs of nodes until the root
     * is reached.
     *
     * @return The root hash of the Merkle Tree.
     */
    private String buildTree() {
        if (transactions.isEmpty()) {
            return "";
        }

        List<String> currentLevel = new ArrayList<>();
        for (String transaction : transactions) {
            currentLevel.add(hash(transaction));
        }

        while (currentLevel.size() > 1) {
            List<String> nextLevel = new ArrayList<>();
            for (int i = 0; i < currentLevel.size(); i += 2) {
                String left = currentLevel.get(i);
                String right = (i + 1 < currentLevel.size()) ? currentLevel.get(i + 1) : left;
                nextLevel.add(hash(left + right));
            }
            currentLevel = nextLevel;
        }

        return currentLevel.get(0);
    }

    /**
     * Retrieves the root hash of the tree.
     *
     * @return The Merkle Root.
     */
    public String getRoot() {
        return root;
    }

    /**
     * Hashes a string using SHA-256.
     *
     * @param data The string to hash.
     * @return The SHA-256 hash in hexadecimal format.
     * @throws RuntimeException if SHA-256 algorithm is not available.
     */
    private String hash(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
