package pe.com.mivoto.service.presentation.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.com.mivoto.service.application.services.ElectionManagementService;
import pe.com.mivoto.service.application.services.VotingService;
import pe.com.mivoto.service.application.usecases.election.CloseElectionUseCaseImpl;
import pe.com.mivoto.service.application.usecases.election.CreateElectionUseCaseImpl;
import pe.com.mivoto.service.application.usecases.election.GetActiveElectionsUseCaseImpl;
import pe.com.mivoto.service.application.usecases.election.GetElectionResultsUseCaseImpl;
import pe.com.mivoto.service.domain.model.Election;
import pe.com.mivoto.service.infrastructure.security.jwt.JwtTokenProvider;
import pe.com.mivoto.service.presentation.dto.request.CreateElectionRequest;
import pe.com.mivoto.service.presentation.dto.response.ApiResponse;
import pe.com.mivoto.service.presentation.dto.response.ElectionResponse;
import pe.com.mivoto.service.presentation.mappers.ElectionDtoMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Test suite for ElectionController.
 * Verifies endpoints for creating and retrieving elections.
 */
@ExtendWith(MockitoExtension.class)
class ElectionControllerTest {

    @Mock
    private CreateElectionUseCaseImpl createElectionUseCase;
    @Mock
    private GetActiveElectionsUseCaseImpl getActiveElectionsUseCase;
    @Mock
    private CloseElectionUseCaseImpl closeElectionUseCase;
    @Mock
    private GetElectionResultsUseCaseImpl getElectionResultsUseCase;
    @Mock
    private ElectionManagementService electionManagementService;
    @Mock
    private VotingService votingService;
    @Mock
    private ElectionDtoMapper electionDtoMapper;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    private ElectionController electionController;

    @BeforeEach
    void setUp() {
        electionController = new ElectionController(
                createElectionUseCase, getActiveElectionsUseCase, closeElectionUseCase,
                getElectionResultsUseCase, electionManagementService, votingService,
                electionDtoMapper, jwtTokenProvider);
    }

    /**
     * Tests the create election endpoint.
     */
    @Test
    @DisplayName("Should successfully create election")
    void testCreateElection() {
        CreateElectionRequest request = new CreateElectionRequest();
        request.setTitle("Election 1");
        String authHeader = "Bearer token";

        when(jwtTokenProvider.getUserIdFromToken("token")).thenReturn(1L);

        Election electionDomain = new Election();
        when(electionDtoMapper.toDomain(request)).thenReturn(electionDomain);

        Election createdElection = new Election();
        when(createElectionUseCase.execute(electionDomain, 1L)).thenReturn(createdElection);

        ElectionResponse responseDto = new ElectionResponse();
        when(electionDtoMapper.toElectionResponse(createdElection, false)).thenReturn(responseDto);

        ResponseEntity<ApiResponse<ElectionResponse>> response = electionController.createElection(request, authHeader);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(responseDto, response.getBody().getData());
    }

    /**
     * Tests closing an election.
     */
    @Test
    @DisplayName("Should successfully close election")
    void testCloseElection() {
        Long electionId = 100L;
        doNothing().when(closeElectionUseCase).execute(electionId);

        ResponseEntity<ApiResponse<Void>> response = electionController.closeElection(electionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(closeElectionUseCase).execute(electionId);
    }
}
