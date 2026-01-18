package pe.com.mivoto.service.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.mivoto.service.application.services.ElectionManagementService;
import pe.com.mivoto.service.application.services.VotingService;
import pe.com.mivoto.service.application.usecases.election.CloseElectionUseCaseImpl;
import pe.com.mivoto.service.application.usecases.election.CreateElectionUseCaseImpl;
import pe.com.mivoto.service.application.usecases.election.GetActiveElectionsUseCaseImpl;
import pe.com.mivoto.service.application.usecases.election.GetElectionResultsUseCaseImpl;
import pe.com.mivoto.service.domain.enums.ElectionStatus;
import pe.com.mivoto.service.domain.model.Candidate;
import pe.com.mivoto.service.domain.model.Election;
import pe.com.mivoto.service.infrastructure.security.jwt.JwtTokenProvider;
import pe.com.mivoto.service.presentation.dto.request.CreateElectionRequestDto;
import pe.com.mivoto.service.presentation.dto.request.UpdateElectionRequestDto;
import pe.com.mivoto.service.presentation.dto.response.ApiResponseDto;
import pe.com.mivoto.service.presentation.dto.response.ElectionResponseDto;
import pe.com.mivoto.service.presentation.dto.response.ElectionResultsResponseDto;
import pe.com.mivoto.service.presentation.mappers.ElectionDtoMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * REST Controller for Election Management.
 * Provides endpoints for creating, retrieving, updating, and managing
 * elections.
 */
@Slf4j
@RestController
@RequestMapping("/api/elections")
@RequiredArgsConstructor
@Tag(name = "Elections", description = "Endpoints de gestión de elecciones")
public class ElectionController {
    private final CreateElectionUseCaseImpl createElectionUseCase;
    private final GetActiveElectionsUseCaseImpl getActiveElectionsUseCase;
    private final CloseElectionUseCaseImpl closeElectionUseCase;
    private final GetElectionResultsUseCaseImpl getElectionResultsUseCase;
    private final ElectionManagementService electionManagementService;
    private final VotingService votingService;
    private final ElectionDtoMapper electionDtoMapper;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Creates a new election.
     *
     * @param request    The election creation request.
     * @param authHeader The Authorization header.
     * @return ResponseEntity containing the created election data.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Crear elección", description = "Crea una nueva elección")
    public ResponseEntity<ApiResponseDto<ElectionResponseDto>> createElection(
            @Valid @RequestBody CreateElectionRequestDto request, @RequestHeader("Authorization") String authHeader) {
        Long userId = getUserIdFromToken(authHeader);
        log.info("Creando elección: {} - Usuario: {}", request.getTitle(), userId);
        Election election = electionDtoMapper.toDomain(request);
        Election created = createElectionUseCase.execute(election, userId);
        ElectionResponseDto response = electionDtoMapper.toElectionResponse(created, false);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Elección creada exitosamente", response));
    }

    /**
     * Retrieves all elections.
     *
     * @param authHeader The Authorization header (optional) to check if user voted.
     * @return ResponseEntity containing a list of all elections.
     */
    @GetMapping
    @Operation(summary = "Listar todas las elecciones", description = "Obtiene todas las elecciones del sistema")
    public ResponseEntity<ApiResponseDto<List<ElectionResponseDto>>> getAllElections(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        log.info("Obteniendo todas las elecciones");
        List<Election> elections = electionManagementService.getElectionsByStatus(null);
        List<ElectionResponseDto> response = elections.stream()
                .map(election -> {
                    boolean hasVoted = false;
                    if (authHeader != null) {
                        Long userId = getUserIdFromToken(authHeader);
                        hasVoted = votingService.hasVoted(userId, election.getId());
                    }
                    return electionDtoMapper.toElectionResponse(election, hasVoted);
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponseDto.success(response));
    }

    /**
     * Retrieves all active elections.
     *
     * @param authHeader The Authorization header (optional) to check if user voted.
     * @return ResponseEntity containing a list of active elections.
     */
    @GetMapping("/active")
    @Operation(summary = "Listar elecciones activas", description = "Obtiene todas las elecciones activas")
    public ResponseEntity<ApiResponseDto<List<ElectionResponseDto>>> getActiveElections(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        log.info("Obteniendo elecciones activas");
        List<Election> elections = getActiveElectionsUseCase.execute();
        List<ElectionResponseDto> response = elections.stream()
                .map(election -> {
                    boolean hasVoted = false;
                    if (authHeader != null) {
                        Long userId = getUserIdFromToken(authHeader);
                        hasVoted = votingService.hasVoted(userId, election.getId());
                    }
                    return electionDtoMapper.toElectionResponse(election, hasVoted);
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponseDto.success(response));
    }

    /**
     * Retrieves a specific election by ID.
     *
     * @param id         The election ID.
     * @param authHeader The Authorization header (optional) to check if user voted.
     * @return ResponseEntity containing the election details.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener elección por ID", description = "Obtiene los detalles de una elección específica")
    public ResponseEntity<ApiResponseDto<ElectionResponseDto>> getElectionById(@PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        log.info("Obteniendo elección: {}", id);
        Election election = electionManagementService.getElectionById(id);
        boolean hasVoted = false;
        if (authHeader != null) {
            Long userId = getUserIdFromToken(authHeader);
            hasVoted = votingService.hasVoted(userId, id);
        }
        ElectionResponseDto response = electionDtoMapper.toElectionResponse(election, hasVoted);
        return ResponseEntity.ok(ApiResponseDto.success(response));
    }

    /**
     * Updates an existing election.
     *
     * @param id      The election ID.
     * @param request The update request data.
     * @return ResponseEntity containing the updated election data.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Actualizar elección", description = "Actualiza una elección existente")
    public ResponseEntity<ApiResponseDto<ElectionResponseDto>> updateElection(@PathVariable Long id,
            @Valid @RequestBody UpdateElectionRequestDto request) {
        log.info("Actualizando elección: {}", id);
        Election updateData = Election.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
        Election updated = electionManagementService.updateElection(id, updateData);
        ElectionResponseDto response = electionDtoMapper.toElectionResponse(updated, false);
        return ResponseEntity.ok(ApiResponseDto.success("Elección actualizada exitosamente", response));
    }

    /**
     * Starts a scheduled election.
     *
     * @param id The election ID.
     * @return ResponseEntity indicating successful start.
     */
    @PostMapping("/{id}/start")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Iniciar elección", description = "Inicia una elección programada")
    public ResponseEntity<ApiResponseDto<Void>> startElection(@PathVariable Long id) {
        log.info("Iniciando elección: {}", id);
        electionManagementService.startElection(id);
        return ResponseEntity.ok(ApiResponseDto.success("Elección iniciada exitosamente", null));
    }

    /**
     * Closes an active election.
     *
     * @param id The election ID.
     * @return ResponseEntity indicating successful closure.
     */
    @PostMapping("/{id}/close")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Cerrar elección", description = "Cierra una elección activa")
    public ResponseEntity<ApiResponseDto<Void>> closeElection(@PathVariable Long id) {
        log.info("Cerrando elección: {}", id);
        closeElectionUseCase.execute(id);
        return ResponseEntity.ok(ApiResponseDto.success("Elección cerrada exitosamente", null));
    }

    /**
     * Cancels an election.
     *
     * @param id     The election ID.
     * @param reason The reason for cancellation.
     * @return ResponseEntity indicating successful cancellation.
     */
    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Cancelar elección", description = "Cancela una elección")
    public ResponseEntity<ApiResponseDto<Void>> cancelElection(@PathVariable Long id,
            @RequestParam(required = false) String reason) {
        log.warn("Cancelando elección: {} - Razón: {}", id, reason);
        electionManagementService.cancelElection(id, reason);
        return ResponseEntity.ok(ApiResponseDto.success("Elección cancelada", null));
    }

    /**
     * Retrieves the results of an election.
     *
     * @param id The election ID.
     * @return ResponseEntity containing the election results.
     */
    @GetMapping("/{id}/results")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Obtener resultados", description = "Obtiene los resultados de una elección")
    public ResponseEntity<ApiResponseDto<ElectionResultsResponseDto>> getElectionResults(@PathVariable Long id) {
        log.info("Obteniendo resultados de elección: {}", id);
        Map<Candidate, Long> results = getElectionResultsUseCase.execute(id);
        Election election = electionManagementService.getElectionById(id);
        ElectionResultsResponseDto response = electionDtoMapper.toElectionResultsResponse(election, results);
        return ResponseEntity.ok(ApiResponseDto.success(response));
    }

    /**
     * Retrieves statistics for an election.
     *
     * @param id The election ID.
     * @return ResponseEntity containing the election statistics.
     */
    @GetMapping("/{id}/statistics")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Obtener estadísticas", description = "Obtiene estadísticas de una elección")
    public ResponseEntity<ApiResponseDto<ElectionManagementService.ElectionStatistics>> getElectionStatistics(
            @PathVariable Long id) {
        log.info("Obteniendo estadísticas de elección: {}", id);
        ElectionManagementService.ElectionStatistics stats = electionManagementService.getElectionStatistics(id);
        return ResponseEntity.ok(ApiResponseDto.success(stats));
    }

    /**
     * Retrieves elections filtered by status.
     *
     * @param status     The election status.
     * @param authHeader The Authorization header (optional).
     * @return ResponseEntity containing a list of filtered elections.
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Filtrar por estado", description = "Obtiene elecciones filtradas por estado")
    public ResponseEntity<ApiResponseDto<List<ElectionResponseDto>>> getElectionsByStatus(
            @PathVariable ElectionStatus status,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        log.info("Obteniendo elecciones con estado: {}", status);
        List<Election> elections = electionManagementService.getElectionsByStatus(status);
        List<ElectionResponseDto> response = elections.stream()
                .map(election -> {
                    boolean hasVoted = false;
                    if (authHeader != null) {
                        Long userId = getUserIdFromToken(authHeader);
                        hasVoted = votingService.hasVoted(userId, election.getId());
                    }
                    return electionDtoMapper.toElectionResponse(election, hasVoted);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDto.success(response));
    }

    private Long getUserIdFromToken(String authHeader) {
        String token = extractToken(authHeader);
        return jwtTokenProvider.getUserIdFromToken(token);
    }

    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return authHeader;
    }
}
