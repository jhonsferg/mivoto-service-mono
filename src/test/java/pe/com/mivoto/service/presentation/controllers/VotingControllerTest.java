package pe.com.mivoto.service.presentation.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.com.mivoto.service.application.usecases.voting.CastVoteUseCaseImpl;
import pe.com.mivoto.service.application.usecases.voting.CheckVotingStatusUseCaseImpl;
import pe.com.mivoto.service.application.usecases.voting.GetVotingHistoryUseCaseImpl;
import pe.com.mivoto.service.application.usecases.voting.VerifyVoteUseCaseImpl;
import pe.com.mivoto.service.domain.model.Vote;
import pe.com.mivoto.service.infrastructure.security.jwt.JwtTokenProvider;
import pe.com.mivoto.service.presentation.dto.request.VoteRequestDto;
import pe.com.mivoto.service.presentation.dto.response.ApiResponseDto;
import pe.com.mivoto.service.presentation.dto.response.VoteResponseDto;
import pe.com.mivoto.service.presentation.mappers.VotingDtoMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * Test suite for VotingController.
 * Verifies endpoints for casting and verifying votes.
 */
@ExtendWith(MockitoExtension.class)
class VotingControllerTest {

    @Mock
    private CastVoteUseCaseImpl castVoteUseCase;
    @Mock
    private VerifyVoteUseCaseImpl verifyVoteUseCase;
    @Mock
    private GetVotingHistoryUseCaseImpl getVotingHistoryUseCase;
    @Mock
    private CheckVotingStatusUseCaseImpl checkVotingStatusUseCase;
    @Mock
    private VotingDtoMapper votingDtoMapper;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private HttpServletRequest httpRequest;

    private VotingController votingController;

    @BeforeEach
    void setUp() {
        votingController = new VotingController(
                castVoteUseCase, verifyVoteUseCase, getVotingHistoryUseCase,
                checkVotingStatusUseCase, votingDtoMapper, jwtTokenProvider);
    }

    /**
     * Tests casting a vote.
     */
    @Test
    @DisplayName("Should successfully cast vote")
    void testCastVote() {
        VoteRequestDto request = new VoteRequestDto();
        request.setElectionId(1L);
        request.setCandidateId(2L);
        String authHeader = "Bearer token";

        when(jwtTokenProvider.getUserIdFromToken("token")).thenReturn(10L);
        when(httpRequest.getHeader("X-Forwarded-For")).thenReturn(null);
        when(httpRequest.getRemoteAddr()).thenReturn("127.0.0.1");

        Vote vote = new Vote();
        when(castVoteUseCase.execute(eq(10L), eq(1L), eq(2L), anyString(), any())).thenReturn(vote);

        VoteResponseDto voteResponse = new VoteResponseDto();
        when(votingDtoMapper.toVoteResponse(vote)).thenReturn(voteResponse);

        ResponseEntity<ApiResponseDto<VoteResponseDto>> response = votingController.castVote(request, authHeader,
                httpRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(voteResponse, response.getBody().getData());
    }

    /**
     * Tests checking voting status.
     */
    @Test
    @DisplayName("Should return voting status")
    void testCheckVotingStatus() {
        Long electionId = 1L;
        String authHeader = "Bearer token";

        when(jwtTokenProvider.getUserIdFromToken("token")).thenReturn(10L);
        when(checkVotingStatusUseCase.execute(10L, 1L)).thenReturn(true);

        ResponseEntity<ApiResponseDto<Boolean>> response = votingController.checkVotingStatus(electionId, authHeader);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getData());
    }
}
