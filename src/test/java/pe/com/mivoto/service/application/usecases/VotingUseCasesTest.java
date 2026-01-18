package pe.com.mivoto.service.application.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.mivoto.service.application.services.VotingService;
import pe.com.mivoto.service.application.usecases.voting.CastVoteUseCaseImpl;
import pe.com.mivoto.service.domain.model.Vote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test suite for Voting Use Cases.
 * Covers the vote casting process.
 */
@ExtendWith(MockitoExtension.class)
class VotingUseCasesTest {

    @Mock
    private VotingService votingService;

    private CastVoteUseCaseImpl castVoteUseCase;

    /**
     * Sets up the test environment.
     */
    @BeforeEach
    void setUp() {
        castVoteUseCase = new CastVoteUseCaseImpl(votingService);
    }

    /**
     * Tests casting a vote.
     * Verifies that the voting service is invoked with valid parameters.
     */
    @Test
    @DisplayName("Should successfully cast a vote")
    void testCastVoteExecution() {
        Long userId = 1L;
        Long electionId = 10L;
        Long candidateId = 5L;
        String ip = "192.168.1.1";
        String agent = "Chrome/90";

        Vote expectedVote = new Vote();
        expectedVote.setId(12345L);

        when(votingService.castVote(userId, electionId, candidateId, ip, agent)).thenReturn(expectedVote);

        Vote result = castVoteUseCase.execute(userId, electionId, candidateId, ip, agent);

        assertNotNull(result);
        assertEquals(12345L, result.getId());
        verify(votingService).castVote(userId, electionId, candidateId, ip, agent);
    }
}
