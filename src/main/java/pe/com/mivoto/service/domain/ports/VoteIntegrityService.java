package pe.com.mivoto.service.domain.ports;

import java.util.List;

public interface VoteIntegrityService {
    String generateIntegrityProof(List<String> voteHashes);

    boolean verifyIntegrity(List<String> voteHashes, String rootHash);
}
