package pe.com.mivoto.service.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class AuditLogTest {
    @Test
    void testAuditLog() {
        LocalDateTime now = LocalDateTime.now();
        AuditLog log = AuditLog.builder()
                .id(1L)
                .action(pe.com.mivoto.service.domain.enums.AuditAction.LOGIN)
                .description("User logged in")
                .userId(5L)
                .timestamp(now)
                .entity("User")
                .entityId(5L)
                .build();

        assertEquals(1L, log.getId());
        assertEquals(pe.com.mivoto.service.domain.enums.AuditAction.LOGIN, log.getAction());
        assertEquals("User logged in", log.getDescription());
        assertEquals(5L, log.getUserId());
        assertEquals(now, log.getTimestamp());
        assertEquals("User", log.getEntity());

        AuditLog log2 = AuditLog.builder().id(1L).action(pe.com.mivoto.service.domain.enums.AuditAction.LOGIN)
                .description("User logged in").userId(5L).timestamp(now).entity("User").entityId(5L).build();
        assertEquals(log, log2);
        assertEquals(log.hashCode(), log2.hashCode());
        assertNotNull(log.toString());
    }
}
