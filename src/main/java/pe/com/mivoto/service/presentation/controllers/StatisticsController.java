package pe.com.mivoto.service.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.mivoto.service.application.services.StatisticsService;
import pe.com.mivoto.service.presentation.dto.response.ApiResponseDto;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Statistics.
 * Provides endpoints to retrieve system, election, and candidate statistics.
 */
@Slf4j
@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
@Tag(name = "Statistics", description = "Endpoints de estadísticas")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
public class StatisticsController {
    private final StatisticsService statisticsService;

    /**
     * Retrieves global system statistics.
     *
     * @return ResponseEntity containing system statistics.
     */
    @GetMapping("/system")
    @Operation(summary = "Estadísticas del sistema", description = "Obtiene estadísticas globales del sistema")
    public ResponseEntity<ApiResponseDto<StatisticsService.SystemStatistics>> getSystemStatistics() {
        log.info("Obteniendo estadísticas del sistema");
        StatisticsService.SystemStatistics stats = statisticsService.getSystemStatistics();
        return ResponseEntity.ok(ApiResponseDto.success(stats));
    }

    /**
     * Retrieves statistics for a specific election.
     *
     * @param electionId The election ID.
     * @return ResponseEntity containing election statistics.
     */
    @GetMapping("/election/{electionId}")
    @Operation(summary = "Estadísticas de elección", description = "Obtiene estadísticas de una elección")
    public ResponseEntity<ApiResponseDto<StatisticsService.ElectionStatistics>> getElectionStatistics(
            @PathVariable Long electionId) {
        log.info("Obteniendo estadísticas de elección: {}", electionId);
        StatisticsService.ElectionStatistics stats = statisticsService.getElectionStatistics(electionId);
        return ResponseEntity.ok(ApiResponseDto.success(stats));
    }

    /**
     * Retrieves statistics for candidates in an election.
     *
     * @param electionId The election ID.
     * @return ResponseEntity containing list of candidate statistics.
     */
    @GetMapping("/election/{electionId}/candidates")
    @Operation(summary = "Estadísticas de candidatos", description = "Obtiene estadísticas de candidatos")
    public ResponseEntity<ApiResponseDto<List<StatisticsService.CandidateStatistics>>> getCandidateStatistics(
            @PathVariable Long electionId) {
        log.info("Obteniendo estadísticas de candidatos - Elección: {}", electionId);
        List<StatisticsService.CandidateStatistics> stats = statisticsService.getCandidateStatistics(electionId);
        return ResponseEntity.ok(ApiResponseDto.success(stats));
    }

    /**
     * Retrieves voting participation statistics by hour.
     *
     * @param electionId The election ID.
     * @return ResponseEntity containing participation map (hour -> count).
     */
    @GetMapping("/election/{electionId}/participation")
    @Operation(summary = "Participación por hora", description = "Obtiene la participación distribuida por horas")
    public ResponseEntity<ApiResponseDto<Map<Integer, Long>>> getVotingParticipationByHour(
            @PathVariable Long electionId) {
        log.info("Obteniendo participación por hora - Elección: {}", electionId);
        Map<Integer, Long> participation = statisticsService.getVotingParticipationByHour(electionId);
        return ResponseEntity.ok(ApiResponseDto.success(participation));
    }
}
