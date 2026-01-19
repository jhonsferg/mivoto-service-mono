package pe.com.mivoto.service.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DistrictTest {
    @Test
    void testDistrict() {
        District d = District.builder()
                .id(1L)
                .name("Lima")
                .type("REGIONAL")
                .code("LIM01")
                .registeredVoters(1000)
                .build();

        assertEquals(1L, d.getId());
        assertEquals("Lima", d.getName());
        assertEquals("REGIONAL", d.getType());
        assertEquals("LIM01", d.getCode());
        assertEquals(1000, d.getRegisteredVoters());

        District d2 = District.builder().id(1L).name("Lima").type("REGIONAL").code("LIM01").registeredVoters(1000)
                .build();
        assertEquals(d, d2);
        assertEquals(d.hashCode(), d2.hashCode());
        assertNotNull(d.toString());
    }
}
