package pe.com.mivoto.service.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class VotingSessionTest {
    @Test
    void testVotingSession() {
        LocalDateTime exp = LocalDateTime.now().plusHours(1);
        VotingSession session = VotingSession.builder()
                .sessionToken("sess123")
                .userId(1L)
                .refreshToken("jwt-token")
                .ipAddress("127.0.0.1")
                .userAgent("Mozilla")
                .expiresAt(exp)
                .active(true)
                .build();

        assertEquals("sess123", session.getSessionToken());
        assertEquals(1L, session.getUserId());
        assertEquals("jwt-token", session.getRefreshToken());
        assertEquals("127.0.0.1", session.getIpAddress());
        assertEquals("Mozilla", session.getUserAgent());
        assertEquals(exp, session.getExpiresAt());
        assertTrue(session.getActive());

        VotingSession s2 = VotingSession.builder().sessionToken("sess123").userId(1L).refreshToken("jwt-token")
                .ipAddress("127.0.0.1").userAgent("Mozilla").expiresAt(exp).active(true).build();
        assertEquals(session, s2);
        assertEquals(session.hashCode(), s2.hashCode());
        assertNotNull(session.toString());
    }
}
