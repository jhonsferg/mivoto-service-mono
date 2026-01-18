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
import pe.com.mivoto.service.application.services.CandidateService;
import pe.com.mivoto.service.domain.model.Candidate;
import pe.com.mivoto.service.presentation.dto.request.CreateCandidateRequestDto;
import pe.com.mivoto.service.presentation.dto.response.ApiResponseDto;
import pe.com.mivoto.service.presentation.dto.response.CandidateDto;
import pe.com.mivoto.service.presentation.mappers.CandidateDtoMapper;

import java.util.List;

/**
 * REST Controller for Candidate Management.
 * Provides endpoints for registering, retrieving, updating, and deleting
 * candidates.
 */
@Slf4j
@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
@Tag(name = "Candidates", description = "Endpoints de gestión de candidatos")
public class CandidateController {
    private final CandidateService candidateService;
    private final CandidateDtoMapper candidateDtoMapper;

    /**
     * Registers a new candidate for an election.
     *
     * @param request The candidate creation request.
     * @return ResponseEntity containing the registered candidate data.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Registrar candidato", description = "Registra un nuevo candidato en una elección")
    public ResponseEntity<ApiResponseDto<CandidateDto>> registerCandidate(
            @Valid @RequestBody CreateCandidateRequestDto request) {
        log.info("Registrando candidato: {} - Elección: {}", request.getName(), request.getElectionId());
        Candidate candidate = candidateDtoMapper.toDomain(request);
        Candidate registered = candidateService.registerCandidate(candidate);
        CandidateDto response = candidateDtoMapper.toCandidateDto(registered);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Candidato registrado exitosamente", response));
    }

    /**
     * Retrieves a candidate by its unique ID.
     *
     * @param id The candidate ID.
     * @return ResponseEntity containing the candidate data.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener candidato", description = "Obtiene un candidato por su ID")
    public ResponseEntity<ApiResponseDto<CandidateDto>> getCandidateById(@PathVariable Long id) {
        log.info("Obteniendo candidato: {}", id);
        Candidate candidate = candidateService.getCandidateById(id);
        CandidateDto response = candidateDtoMapper.toCandidateDto(candidate);
        return ResponseEntity.ok(ApiResponseDto.success(response));
    }

    /**
     * Retrieves all candidates for a specific election.
     *
     * @param electionId The election ID.
     * @return ResponseEntity containing a list of candidates.
     */
    @GetMapping("/election/{electionId}")
    @Operation(summary = "Listar candidatos", description = "Obtiene todos los candidatos de una elección")
    public ResponseEntity<ApiResponseDto<List<CandidateDto>>> getCandidatesByElection(@PathVariable Long electionId) {
        log.info("Obteniendo candidatos de elección: {}", electionId);
        List<Candidate> candidates = candidateService.getCandidatesByElection(electionId);
        List<CandidateDto> response = candidateDtoMapper.toCandidateDtoList(candidates);
        return ResponseEntity.ok(ApiResponseDto.success(response));
    }

    /**
     * Retrieves active candidates for a specific election.
     *
     * @param electionId The election ID.
     * @return ResponseEntity containing a list of active candidates.
     */
    @GetMapping("/election/{electionId}/active")
    @Operation(summary = "Listar candidatos activos", description = "Obtiene candidatos activos de una elección")
    public ResponseEntity<ApiResponseDto<List<CandidateDto>>> getActiveCandidates(@PathVariable Long electionId) {
        log.info("Obteniendo candidatos activos de elección: {}", electionId);
        List<Candidate> candidates = candidateService.getActiveCandidates(electionId);
        List<CandidateDto> response = candidateDtoMapper.toCandidateDtoList(candidates);
        return ResponseEntity.ok(ApiResponseDto.success(response));
    }

    /**
     * Finds a candidate by their number in a specific election.
     *
     * @param electionId The election ID.
     * @param number     The candidate number.
     * @return ResponseEntity containing the candidate data.
     */
    @GetMapping("/election/{electionId}/number/{number}")
    @Operation(summary = "Buscar por número", description = "Busca un candidato por su número en una elección")
    public ResponseEntity<ApiResponseDto<CandidateDto>> findCandidateByNumber(@PathVariable Long electionId,
            @PathVariable Integer number) {
        log.info("Buscando candidato número {} en elección {}", number, electionId);
        Candidate candidate = candidateService.findCandidateByNumber(electionId, number);
        CandidateDto response = candidateDtoMapper.toCandidateDto(candidate);
        return ResponseEntity.ok(ApiResponseDto.success(response));
    }

    /**
     * Retrieves candidates for an election ordered by their number.
     *
     * @param electionId The election ID.
     * @return ResponseEntity containing a list of ordered candidates.
     */
    @GetMapping("/election/{electionId}/ordered")
    @Operation(summary = "Listar ordenados", description = "Obtiene candidatos ordenados por número")
    public ResponseEntity<ApiResponseDto<List<CandidateDto>>> getCandidatesOrderedByNumber(
            @PathVariable Long electionId) {
        log.info("Obteniendo candidatos ordenados de elección: {}", electionId);
        List<Candidate> candidates = candidateService.getCandidatesOrderedByNumber(electionId);
        List<CandidateDto> response = candidateDtoMapper.toCandidateDtoList(candidates);
        return ResponseEntity.ok(ApiResponseDto.success(response));
    }

    /**
     * Finds candidates belonging to a specific political party.
     *
     * @param party The party name.
     * @return ResponseEntity containing a list of candidates.
     */
    @GetMapping("/party/{party}")
    @Operation(summary = "Buscar por partido", description = "Busca candidatos por partido político")
    public ResponseEntity<ApiResponseDto<List<CandidateDto>>> findByParty(@PathVariable String party) {
        log.info("Buscando candidatos del partido: {}", party);
        List<Candidate> candidates = candidateService.findCandidatesByParty(party);
        List<CandidateDto> response = candidateDtoMapper.toCandidateDtoList(candidates);
        return ResponseEntity.ok(ApiResponseDto.success(response));
    }

    /**
     * Updates an existing candidate.
     *
     * @param id      The candidate ID.
     * @param request The update request data.
     * @return ResponseEntity containing the updated candidate data.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Actualizar candidato", description = "Actualiza los datos de un candidato")
    public ResponseEntity<ApiResponseDto<CandidateDto>> updateCandidate(@PathVariable Long id,
            @Valid @RequestBody CreateCandidateRequestDto request) {
        log.info("Actualizando candidato: {}", id);
        Candidate updateData = candidateDtoMapper.toDomain(request);
        Candidate updated = candidateService.updateCandidate(id, updateData);
        CandidateDto response = candidateDtoMapper.toCandidateDto(updated);
        return ResponseEntity.ok(ApiResponseDto.success("Candidato actualizado exitosamente", response));
    }

    /**
     * Activates a disabled candidate.
     *
     * @param id The candidate ID.
     * @return ResponseEntity indicating successful activation.
     */
    @PostMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Activar candidato", description = "Activa un candidato desactivado")
    public ResponseEntity<ApiResponseDto<Void>> activateCandidate(@PathVariable Long id) {
        log.info("Activando candidato: {}", id);
        candidateService.activateCandidate(id);
        return ResponseEntity.ok(ApiResponseDto.success("Candidato activado exitosamente", null));
    }

    /**
     * Deactivates a candidate.
     *
     * @param id The candidate ID.
     * @return ResponseEntity indicating successful deactivation.
     */
    @PostMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Desactivar candidato", description = "Desactiva un candidato")
    public ResponseEntity<ApiResponseDto<Void>> deactivateCandidate(@PathVariable Long id) {
        log.info("Desactivando candidato: {}", id);
        candidateService.deactivateCandidate(id);
        return ResponseEntity.ok(ApiResponseDto.success("Candidato desactivado exitosamente", null));
    }

    /**
     * Deletes a candidate.
     * Candidate can only be deleted if they have no votes.
     *
     * @param id The candidate ID.
     * @return ResponseEntity indicating successful deletion.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Eliminar candidato", description = "Elimina un candidato (solo si no tiene votos)")
    public ResponseEntity<ApiResponseDto<Void>> deleteCandidate(@PathVariable Long id) {
        log.warn("Eliminando candidato: {}", id);
        candidateService.deleteCandidate(id);
        return ResponseEntity.ok(ApiResponseDto.success("Candidato eliminado exitosamente", null));
    }
}
