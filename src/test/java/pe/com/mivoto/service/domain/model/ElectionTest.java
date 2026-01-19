package pe.com.mivoto.service.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

import pe.com.mivoto.service.domain.enums.ElectionStatus;

class ElectionTest {

    @Test
    void testElectionBuilderAndGetters() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(1);
        Election election = Election.builder()
                .id(100L)
                .title("General Election")
                .description("National voting")
                .startDate(start)
                .endDate(end)
                .status(ElectionStatus.ACTIVE)
                .createdAt(start)
                .updatedAt(start)
                .build();

        assertEquals(100L, election.getId());
        assertEquals("General Election", election.getTitle());
        assertEquals("National voting", election.getDescription());
        assertEquals(start, election.getStartDate());
        assertEquals(end, election.getEndDate());
        assertEquals(ElectionStatus.ACTIVE, election.getStatus());
        assertEquals(start, election.getCreatedAt());
        assertEquals(start, election.getUpdatedAt());
    }

    @Test
    void testSetters() {
        Election election = new Election();
        election.setTitle("New Title");
        assertEquals("New Title", election.getTitle());
    }

    @Test
    void testEqualsAndHashCode() {
        Election e1 = Election.builder().id(1L).title("A").build();
        Election e2 = Election.builder().id(1L).title("A").build();
        Election e3 = Election.builder().id(2L).title("B").build();

        assertEquals(e1, e2);
        assertNotEquals(e1, e3);
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    void testToString() {
        Election e = new Election();
        assertNotNull(e.toString());
    }
}
