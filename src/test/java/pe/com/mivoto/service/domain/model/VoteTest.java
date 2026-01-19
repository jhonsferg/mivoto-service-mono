package pe.com.mivoto.service.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class VoteTest {
    @Test
    void testVote() {
        LocalDateTime now = LocalDateTime.now();
        Vote v = Vote.builder()
                .id(1L)
                .electionId(100L)
                .userId(50L)
                .candidateId(20L)
                .votedAt(now)
                .voteHash("sig123")
                .build();

        assertEquals(1L, v.getId());
        assertEquals(100L, v.getElectionId());
        assertEquals(50L, v.getUserId());
        assertEquals(20L, v.getCandidateId());
        assertEquals(now, v.getVotedAt());
        assertEquals("sig123", v.getVoteHash());

        Vote v2 = new Vote();
        v2.setId(1L);
        v2.setElectionId(100L);
        v2.setUserId(50L);
        v2.setCandidateId(20L);
        v2.setVotedAt(now);
        v2.setVoteHash("sig123");

        assertEquals(v, v2);
        assertEquals(v.hashCode(), v2.hashCode());
        assertNotNull(v.toString());
    }
}
