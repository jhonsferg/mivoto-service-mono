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

    @Test
    @DisplayName("Should check voting status")
    void testCheckVotingStatus() {
        Long userId = 1L;
        Long electionId = 10L;
        pe.com.mivoto.service.application.usecases.voting.CheckVotingStatusUseCaseImpl checkStatusUseCase = new pe.com.mivoto.service.application.usecases.voting.CheckVotingStatusUseCaseImpl(
                votingService);

        when(votingService.hasVoted(userId, electionId)).thenReturn(true);

        boolean result = checkStatusUseCase.execute(userId, electionId);

        assertEquals(true, result);
        verify(votingService).hasVoted(userId, electionId);
    }

    @Test
    @DisplayName("Should get voting history")
    void testGetVotingHistory() {
        Long userId = 1L;
        java.util.List<pe.com.mivoto.service.domain.model.VoteRecord> history = java.util.Collections.singletonList(
                pe.com.mivoto.service.domain.model.VoteRecord.builder().id(1L).build());
        pe.com.mivoto.service.application.usecases.voting.GetVotingHistoryUseCaseImpl getHistoryUseCase = new pe.com.mivoto.service.application.usecases.voting.GetVotingHistoryUseCaseImpl(
                votingService);

        when(votingService.getVotingHistory(userId)).thenReturn(history);

        java.util.List<pe.com.mivoto.service.domain.model.VoteRecord> result = getHistoryUseCase.execute(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId()); // Assuming usage of Mapper internally or manual mapping in UseCase
        verify(votingService).getVotingHistory(userId);
    }

    @Test
    @DisplayName("Should verify vote")
    void testVerifyVote() {
        String voteHash = "hash123";
        pe.com.mivoto.service.domain.model.VoteRecord expectedRecord = pe.com.mivoto.service.domain.model.VoteRecord
                .builder().voteHash(voteHash).build();

        pe.com.mivoto.service.application.usecases.voting.VerifyVoteUseCaseImpl verifyVoteUseCase = new pe.com.mivoto.service.application.usecases.voting.VerifyVoteUseCaseImpl(
                votingService);

        when(votingService.verifyVote(voteHash)).thenReturn(expectedRecord);

        pe.com.mivoto.service.domain.model.VoteRecord result = verifyVoteUseCase.execute(voteHash);

        assertNotNull(result);
        assertEquals(voteHash, result.getVoteHash());
        verify(votingService).verifyVote(voteHash);
    }
}
