package pe.com.mivoto.service.presentation.dto.response;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pe.com.mivoto.service.domain.enums.ElectionStatus;
import pe.com.mivoto.service.domain.enums.UserRole;
import pe.com.mivoto.service.domain.enums.VoteStatus;
import java.time.LocalDateTime;

class ResponseDtoTest {

    @Nested
    class ApiResponseDtoTest {
        @Test
        void testApiResponseDto() {
            ApiResponseDto<String> dto = ApiResponseDto.success("msg", "data");
            assertTrue(dto.getSuccess());
            assertEquals("msg", dto.getMessage());
            assertEquals("data", dto.getData());

            ApiResponseDto<String> err = ApiResponseDto.error("err");
            assertFalse(err.getSuccess());
            assertEquals("err", err.getMessage());
        }
    }

    @Nested
    class UserDtoTest {
        @Test
        void testUserDto() {
            LocalDateTime now = LocalDateTime.now();
            UserDto dto = UserDto.builder()
                    .id(1L)
                    .firstName("Jhon")
                    .lastName("Doe")
                    .documentNumber("12345678")
                    .email("jhon@example.com")
                    .role(UserRole.VOTER)
                    .active(true)
                    .lastLogin(now)
                    .build();

            assertEquals(1L, dto.getId());
            assertEquals("Jhon", dto.getFirstName());
            assertEquals("Doe", dto.getLastName());
            assertEquals("12345678", dto.getDocumentNumber());
            assertEquals("jhon@example.com", dto.getEmail());
            assertEquals(UserRole.VOTER, dto.getRole());
            assertTrue(dto.getActive());
            assertEquals(now, dto.getLastLogin());
            assertNotNull(dto.toString());
        }
    }

    @Nested
    class LoginResponseDtoTest {
        @Test
        void testLoginResponseDto() {
            LocalDateTime now = LocalDateTime.now();
            UserDto user = UserDto.builder().id(1L).build();
            LoginResponseDto dto = LoginResponseDto.builder()
                    .accessToken("access")
                    .refreshToken("refresh")
                    .expiresAt(now)
                    .user(user)
                    .build();

            assertEquals("access", dto.getAccessToken());
            assertEquals("refresh", dto.getRefreshToken());
            assertEquals("Bearer", dto.getTokenType());
            assertEquals(now, dto.getExpiresAt());
            assertEquals(user, dto.getUser());
            assertNotNull(dto.toString());
        }
    }

    @Nested
    class CandidateDtoTest {
        @Test
        void testCandidateDto() {
            CandidateDto dto = CandidateDto.builder()
                    .id(1L)
                    .name("Name")
                    .party("Party")
                    .electionId(10L)
                    .active(true)
                    .number(1)
                    .voteCount(100)
                    .build();

            assertEquals(1L, dto.getId());
            assertEquals("Name", dto.getName());
            assertEquals("Party", dto.getParty());
            assertEquals(10L, dto.getElectionId());
            assertTrue(dto.getActive());
            assertEquals(1, dto.getNumber());
            assertEquals(100, dto.getVoteCount());
            assertNotNull(dto.toString());
        }
    }

    @Nested
    class ElectionResponseDtoTest {
        @Test
        void testElectionResponseDto() {
            LocalDateTime now = LocalDateTime.now();
            ElectionResponseDto dto = ElectionResponseDto.builder()
                    .id(1L)
                    .title("Title")
                    .description("Desc")
                    .status(ElectionStatus.ACTIVE)
                    .startDate(now)
                    .endDate(now.plusDays(1))
                    .hasVoted(false)
                    .build();

            assertEquals(1L, dto.getId());
            assertEquals("Title", dto.getTitle());
            assertEquals("Desc", dto.getDescription());
            assertEquals(ElectionStatus.ACTIVE, dto.getStatus());
            assertEquals(now, dto.getStartDate());
            assertEquals(now.plusDays(1), dto.getEndDate());
            assertFalse(dto.getHasVoted());
            assertNotNull(dto.toString());
        }
    }

    @Nested
    class ElectionResultsResponseDtoTest {
        @Test
        void testElectionResultsResponseDto() {
            ElectionResultsResponseDto dto = ElectionResultsResponseDto.builder()
                    .electionId(1L)
                    .electionTitle("Title")
                    .totalVotes(1000L)
                    .build();

            assertEquals(1L, dto.getElectionId());
            assertEquals("Title", dto.getElectionTitle());
            assertEquals(1000L, dto.getTotalVotes());
            assertNotNull(dto.toString());
        }
    }

    @Nested
    class ErrorResponseDtoTest {
        @Test
        void testErrorResponseDto() {
            LocalDateTime now = LocalDateTime.now();
            ErrorResponseDto dto = ErrorResponseDto.builder()
                    .timestamp(now)
                    .status(400)
                    .error("Bad Request")
                    .message("Msg")
                    .path("/path")
                    .build();

            assertEquals(now, dto.getTimestamp());
            assertEquals(400, dto.getStatus());
            assertEquals("Bad Request", dto.getError());
            assertEquals("Msg", dto.getMessage());
            assertEquals("/path", dto.getPath());
            assertNotNull(dto.toString());
        }
    }

    @Nested
    class VoteRecordDtoTest {
        @Test
        void testVoteRecordDto() {
            LocalDateTime now = LocalDateTime.now();
            VoteRecordDto dto = VoteRecordDto.builder()
                    .id(1L)
                    .voteId(2L)
                    .electionId(3L)
                    .voteHash("hash")
                    .timestamp(now)
                    .verified(true)
                    .build();

            assertEquals(1L, dto.getId());
            assertEquals(2L, dto.getVoteId());
            assertEquals(3L, dto.getElectionId());
            assertEquals("hash", dto.getVoteHash());
            assertEquals(now, dto.getTimestamp());
            assertTrue(dto.getVerified());
            assertNotNull(dto.toString());
        }
    }

    @Nested
    class VoteResponseDtoTest {
        @Test
        void testVoteResponseDto() {
            LocalDateTime now = LocalDateTime.now();
            VoteResponseDto dto = VoteResponseDto.builder()
                    .id(1L)
                    .voteHash("hash")
                    .status(VoteStatus.CONFIRMED)
                    .verified(true)
                    .votedAt(now)
                    .verificationCode("CODE")
                    .build();

            assertEquals(1L, dto.getId());
            assertEquals("hash", dto.getVoteHash());
            assertEquals(VoteStatus.CONFIRMED, dto.getStatus());
            assertTrue(dto.getVerified());
            assertEquals(now, dto.getVotedAt());
            assertEquals("CODE", dto.getVerificationCode());
            assertNotNull(dto.toString());
        }
    }
}
