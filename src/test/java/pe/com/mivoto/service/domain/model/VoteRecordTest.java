package pe.com.mivoto.service.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VoteRecordTest {
    @Test
    void testVoteRecord() {
        VoteRecord vr = VoteRecord.builder()
                .electionId(1L)
                .voteId(2L)
                .voteHash("hash123")
                .build();

        assertEquals(1L, vr.getElectionId());
        assertEquals(2L, vr.getVoteId());
        assertEquals("hash123", vr.getVoteHash());

        VoteRecord vr2 = VoteRecord.builder().electionId(1L).voteId(2L).voteHash("hash123").build();
        assertEquals(vr, vr2);
        assertEquals(vr.hashCode(), vr2.hashCode());
        assertNotNull(vr.toString());
    }
}
