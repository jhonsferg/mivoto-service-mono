package pe.com.mivoto.service.datastructures.nonlinear.tree;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MerkleTree {
    private List<String> transactions;
    private List<String> tree;
    private String root;

    public MerkleTree(List<String> transactions) {
        this.transactions = new ArrayList<>(transactions);
        this.tree = new ArrayList<>();
        this.root = buildTree();
    }

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

    public String getRoot() {
        return root;
    }

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
