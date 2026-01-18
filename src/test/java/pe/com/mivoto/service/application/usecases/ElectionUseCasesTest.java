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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
}
