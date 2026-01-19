package pe.com.mivoto.service.application.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.mivoto.service.application.services.ElectionManagementService;
import pe.com.mivoto.service.application.usecases.election.CreateElectionUseCaseImpl;
import pe.com.mivoto.service.domain.model.Election;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

/**
 * Test suite for Election Management Use Cases.
 * Covers election creation logic.
 */
@ExtendWith(MockitoExtension.class)
class ElectionUseCasesTest {

    @Mock
    private ElectionManagementService electionService;

    private CreateElectionUseCaseImpl createElectionUseCase;

    /**
     * Sets up the test environment.
     */
    @BeforeEach
    void setUp() {
        createElectionUseCase = new CreateElectionUseCaseImpl(electionService);
    }

    /**
     * Tests the creation of a new election.
     * Verifies that the service delegates correctly to the management service.
     */
    @Test
    @DisplayName("Should successfully create a new election")
    void testCreateElectionExecution() {
        Election election = new Election();
        election.setTitle("General Elections 2026");
        Long createdBy = 1L;

        Election createdElection = new Election();
        createdElection.setId(100L);
        createdElection.setTitle("General Elections 2026");

        when(electionService.createElection(election, createdBy)).thenReturn(createdElection);

        Election result = createElectionUseCase.execute(election, createdBy);

        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals("General Elections 2026", result.getTitle());
        verify(electionService).createElection(election, createdBy);
    }

    @Test
    @DisplayName("Should successfully close an election")
    void testCloseElectionExecution() {
        Long electionId = 100L;

        pe.com.mivoto.service.application.usecases.election.CloseElectionUseCaseImpl closeUseCase = new pe.com.mivoto.service.application.usecases.election.CloseElectionUseCaseImpl(
                electionService);

        doNothing().when(electionService).closeElection(electionId);

        closeUseCase.execute(electionId);

        verify(electionService).closeElection(electionId);
    }

    @Test
    @DisplayName("Should get active elections")
    void testGetActiveElections() {
        java.util.List<Election> elections = java.util.Collections.singletonList(new Election());
        pe.com.mivoto.service.application.usecases.election.GetActiveElectionsUseCaseImpl getActiveUseCase = new pe.com.mivoto.service.application.usecases.election.GetActiveElectionsUseCaseImpl(
                electionService);

        when(electionService.getActiveElections()).thenReturn(elections);

        java.util.List<Election> result = getActiveUseCase.execute();

        assertEquals(elections, result);
        verify(electionService).getActiveElections();
    }

    @Test
    @DisplayName("Should get election results")
    void testGetElectionResults() {
        Long electionId = 100L;
        java.util.Map<pe.com.mivoto.service.domain.model.Candidate, Long> mockResults = new java.util.HashMap<>();
        pe.com.mivoto.service.domain.model.Candidate c = new pe.com.mivoto.service.domain.model.Candidate();
        c.setId(1L);
        c.setName("C1");
        c.setParty("P1");
        mockResults.put(c, 100L);

        // We don't need Election here if UseCase doesn't fetch it or if it just returns
        // Map
        // Checked implementation: it just delegates to service.getElectionResults

        pe.com.mivoto.service.application.usecases.election.GetElectionResultsUseCaseImpl getResultsUseCase = new pe.com.mivoto.service.application.usecases.election.GetElectionResultsUseCaseImpl(
                electionService);

        when(electionService.getElectionResults(electionId)).thenReturn(mockResults);

        java.util.Map<pe.com.mivoto.service.domain.model.Candidate, Long> result = getResultsUseCase
                .execute(electionId);

        assertNotNull(result);
        assertEquals(mockResults, result);
        verify(electionService).getElectionResults(electionId);
    }
}
