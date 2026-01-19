package pe.com.mivoto.service.presentation.dto.request;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RequestDtoTest {

    @Nested
    class LoginRequestDtoTest {
        @Test
        void testLoginRequestDto() {
            LoginRequestDto dto = new LoginRequestDto();
            dto.setUsername("user");
            dto.setPassword("pass");

            assertEquals("user", dto.getUsername());
            assertEquals("pass", dto.getPassword());
            assertNotNull(dto.toString());
        }
    }

    @Nested
    class ChangePasswordRequestDtoTest {
        @Test
        void testChangePasswordRequestDto() {
            ChangePasswordRequestDto dto = new ChangePasswordRequestDto();
            dto.setOldPassword("old");
            dto.setNewPassword("new");

            assertEquals("old", dto.getOldPassword());
            assertEquals("new", dto.getNewPassword());
            assertNotNull(dto.toString());
        }
    }

    @Nested
    class CreateCandidateRequestDtoTest {
        @Test
        void testCreateCandidateRequestDto() {
            CreateCandidateRequestDto dto = new CreateCandidateRequestDto();
            dto.setName("Name");
            dto.setParty("Party");
            dto.setElectionId(1L);

            assertEquals("Name", dto.getName());
            assertEquals("Party", dto.getParty());
            assertEquals(1L, dto.getElectionId());
            assertNotNull(dto.toString());
        }
    }

    @Nested
    class CreateElectionRequestDtoTest {
        @Test
        void testCreateElectionRequestDto() {
            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            CreateElectionRequestDto dto = new CreateElectionRequestDto();
            dto.setTitle("Title");
            dto.setDescription("Desc");
            dto.setStartDate(now);
            dto.setEndDate(now.plusDays(1));

            assertEquals("Title", dto.getTitle());
            assertEquals("Desc", dto.getDescription());
            assertEquals(now, dto.getStartDate());
            assertEquals(now.plusDays(1), dto.getEndDate());
            assertNotNull(dto.toString());
        }
    }

    @Nested
    class RefreshTokenRequestDtoTest {
        @Test
        void testRefreshTokenRequestDto() {
            RefreshTokenRequestDto dto = new RefreshTokenRequestDto();
            dto.setRefreshToken("token");
            assertEquals("token", dto.getRefreshToken());
            assertNotNull(dto.toString());
        }
    }

    @Nested
    class UpdateElectionRequestDtoTest {
        @Test
        void testUpdateElectionRequestDto() {
            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            UpdateElectionRequestDto dto = new UpdateElectionRequestDto();
            dto.setTitle("Title");
            dto.setDescription("Desc");
            dto.setStartDate(now);
            dto.setEndDate(now.plusDays(1));

            assertEquals("Title", dto.getTitle());
            assertEquals("Desc", dto.getDescription());
            assertEquals(now, dto.getStartDate());
            assertEquals(now.plusDays(1), dto.getEndDate());
            assertNotNull(dto.toString());
        }
    }

    @Nested
    class VoteRequestDtoTest {
        @Test
        void testVoteRequestDto() {
            VoteRequestDto dto = new VoteRequestDto();
            dto.setElectionId(1L);
            dto.setCandidateId(2L);

            assertEquals(1L, dto.getElectionId());
            assertEquals(2L, dto.getCandidateId());
            assertNotNull(dto.toString());
        }
    }
}
