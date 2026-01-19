package pe.com.mivoto.service.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CandidateTest {
    @Test
    void testCandidate() {
        Candidate c = Candidate.builder()
                .id(1L)
                .name("Candidate A")
                .party("Party A")
                .electionId(10L)
                .active(true)
                .number(1)
                .description("Desc")
                .build();

        assertEquals(1L, c.getId());
        assertEquals("Candidate A", c.getName());
        assertEquals("Party A", c.getParty());
        assertEquals(10L, c.getElectionId());
        assertTrue(c.getActive());
        assertEquals(1, c.getNumber());
        assertEquals("Desc", c.getDescription());

        Candidate c2 = Candidate.builder().id(1L).name("Candidate A").party("Party A").electionId(10L).active(true)
                .number(1).description("Desc").build();
        assertEquals(c, c2);
        assertEquals(c.hashCode(), c2.hashCode());
        assertNotNull(c.toString());
    }
}
