package pe.com.mivoto.service.presentation.mappers;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pe.com.mivoto.service.domain.model.*;
import pe.com.mivoto.service.presentation.dto.request.*;
import pe.com.mivoto.service.presentation.dto.response.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class MapperTest {

    @Nested
    class AuthDtoMapperTest {
        private final AuthDtoMapper mapper = new AuthDtoMapper();

        @Test
        void testToLoginResponse() {
            LocalDateTime now = LocalDateTime.now();
            VotingSession session = VotingSession.builder()
                    .sessionToken("tok")
                    .refreshToken("ref")
                    .expiresAt(now)
                    .build();
            User user = User.builder().id(1L).build();

            LoginResponseDto dto = mapper.toLoginResponse(session, user);

            assertEquals("tok", dto.getAccessToken());
            assertEquals("ref", dto.getRefreshToken());
            assertEquals(1L, dto.getUser().getId());
        }

        @Test
        void testToUserDto() {
            User user = User.builder()
                    .id(1L)
                    .firstName("Jhon")
                    .lastName("Doe")
                    .documentNumber("12345678")
                    .email("jhon@example.com")
                    .role(pe.com.mivoto.service.domain.enums.UserRole.VOTER)
                    .active(true)
                    .build();

            UserDto dto = mapper.toUserDto(user);

            assertEquals(1L, dto.getId());
            assertEquals("Jhon", dto.getFirstName());
            assertEquals("Doe", dto.getLastName());
            assertEquals("12345678", dto.getDocumentNumber());
            assertEquals("jhon@example.com", dto.getEmail());
            assertEquals(pe.com.mivoto.service.domain.enums.UserRole.VOTER, dto.getRole());
            assertTrue(dto.getActive());
        }
    }

    @Nested
    class CandidateDtoMapperTest {
        private final CandidateDtoMapper mapper = new CandidateDtoMapper();

        @Test
        void testToDomain() {
            CreateCandidateRequestDto req = new CreateCandidateRequestDto();
            req.setName("C");
            req.setParty("P");
            req.setElectionId(10L);

            Candidate c = mapper.toDomain(req);
            assertEquals("C", c.getName());
            assertEquals("P", c.getParty());
            assertEquals(10L, c.getElectionId());
        }

        @Test
        void testToCandidateDto() {
            Candidate c = Candidate.builder().id(1L).name("C").build();
            CandidateDto dto = mapper.toCandidateDto(c);
            assertEquals(1L, dto.getId());
            assertEquals("C", dto.getName());
        }

        @Test
        void testToCandidateDtoList() {
            Candidate c = Candidate.builder().id(1L).build();
            List<CandidateDto> list = mapper.toCandidateDtoList(Collections.singletonList(c));
            assertEquals(1, list.size());
            assertEquals(1L, list.get(0).getId());
        }
    }

    @Nested
    class ElectionDtoMapperTest {
        // Using real dependency since it has no external deps
        private final ElectionDtoMapper mapper = new ElectionDtoMapper(new CandidateDtoMapper());

        @Test
        void testToDomain() {
            CreateElectionRequestDto req = new CreateElectionRequestDto();
            req.setTitle("T");
            Election e = mapper.toDomain(req);
            assertEquals("T", e.getTitle());
        }

        @Test
        void testToElectionResponse() {
            Election e = Election.builder().id(1L).title("T").build();
            ElectionResponseDto dto = mapper.toElectionResponse(e, true);
            assertEquals(1L, dto.getId());
            assertEquals("T", dto.getTitle());
            assertTrue(dto.getHasVoted());
        }

        @Test
        void testToElectionResultsResponse() {
            Election e = Election.builder().id(1L).title("T").build();
            Candidate c = Candidate.builder().id(2L).name("C").build();
            Map<Candidate, Long> results = Collections.singletonMap(c, 10L);

            ElectionResultsResponseDto dto = mapper.toElectionResultsResponse(e, results);

            assertEquals(1L, dto.getElectionId());
            assertEquals(10L, dto.getTotalVotes());
            assertEquals(1, dto.getResults().size());
            assertEquals("C", dto.getResults().get(0).getCandidateName());
        }
    }

    @Nested
    class VotingDtoMapperTest {
        private final VotingDtoMapper mapper = new VotingDtoMapper();

        @Test
        void testToVoteResponse() {
            LocalDateTime now = LocalDateTime.now();
            Vote vote = Vote.builder()
                    .id(1L)
                    .electionId(2L)
                    .candidateId(3L)
                    .voteHash("hash")
                    .status(pe.com.mivoto.service.domain.enums.VoteStatus.CONFIRMED)
                    .verified(true)
                    .votedAt(now)
                    .build();

            VoteResponseDto dto = mapper.toVoteResponse(vote);

            assertNotNull(dto);
            assertEquals(1L, dto.getId());
            assertEquals(2L, dto.getElectionId());
            assertEquals("hash", dto.getVoteHash());
            assertEquals(pe.com.mivoto.service.domain.enums.VoteStatus.CONFIRMED, dto.getStatus());
            assertEquals(now, dto.getVotedAt());
        }

        @Test
        void testToVoteRecordDto() {
            LocalDateTime now = LocalDateTime.now();
            VoteRecord voteRecord = VoteRecord.builder()
                    .id(10L)
                    .voteId(1L)
                    .electionId(2L)
                    .voteHash("hash")
                    .timestamp(now)
                    .verified(true)
                    .build();

            VoteRecordDto dto = mapper.toVoteRecordDto(voteRecord);

            assertNotNull(dto);
            assertEquals(10L, dto.getId());
            assertEquals(1L, dto.getVoteId());
            assertEquals(2L, dto.getElectionId());
            assertEquals("hash", dto.getVoteHash());
            assertEquals(now, dto.getTimestamp());
        }
    }
}
