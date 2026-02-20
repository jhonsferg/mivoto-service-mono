package pe.com.mivoto.service.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.mivoto.service.application.services.AuditService;
import pe.com.mivoto.service.domain.enums.AuditAction;
import pe.com.mivoto.service.domain.model.AuditLog;
import pe.com.mivoto.service.presentation.dto.response.ApiResponseDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST Controller for Audit Logs.
 * Provides endpoints to retrieve audit trails by user, entity, action, date
 * range, etc.
 */
@Slf4j
@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
@Tag(name = "Audit", description = "Endpoints de auditoría")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
public class AuditController {
    private final AuditService auditService;

    /**
     * Retrieves all audit logs.
     *
     * @return ResponseEntity containing a list of all audit logs.
     */
    @GetMapping
    @Operation(summary = "Listar auditorías", description = "Obtiene todos los logs de auditoría del sistema")
    public ResponseEntity<ApiResponseDto<List<AuditLog>>> getAllAuditLogs() {
        log.info("Obteniendo todos los logs de auditoría");
        List<AuditLog> logs = auditService.getAllAuditLogs();
        return ResponseEntity.ok(ApiResponseDto.success(logs));
    }

    /**
     * Retrieves audit logs for a specific user.
     *
     * @param userId The ID of the user.
     * @return ResponseEntity containing a list of audit logs.
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Auditoría por usuario", description = "Obtiene logs de auditoría de un usuario")
    public ResponseEntity<ApiResponseDto<List<AuditLog>>> getAuditTrailByUser(@PathVariable Long userId) {
        log.info("Obteniendo auditoría de usuario: {}", userId);
        List<AuditLog> logs = auditService.getAuditTrailByUser(userId);
        return ResponseEntity.ok(ApiResponseDto.success(logs));
    }

    /**
     * Retrieves audit logs for a specific entity.
     *
     * @param entity   The entity name.
     * @param entityId The entity ID.
     * @return ResponseEntity containing a list of audit logs.
     */
    @GetMapping("/entity/{entity}/{entityId}")
    @Operation(summary = "Auditoría por entidad", description = "Obtiene logs de auditoría de una entidad")
    public ResponseEntity<ApiResponseDto<List<AuditLog>>> getAuditTrailByEntity(@PathVariable String entity,
                                                                                @PathVariable Long entityId) {
        log.info("Obteniendo auditoría de entidad: {}/{}", entity, entityId);
        List<AuditLog> logs = auditService.getAuditTrailByEntity(entity, entityId);
        return ResponseEntity.ok(ApiResponseDto.success(logs));
    }

    /**
     * Retrieves audit logs by action type.
     *
     * @param action The type of action performed.
     * @return ResponseEntity containing a list of audit logs.
     */
    @GetMapping("/action/{action}")
    @Operation(summary = "Auditoría por acción", description = "Obtiene logs de auditoría por tipo de acción")
    public ResponseEntity<ApiResponseDto<List<AuditLog>>> getAuditTrailByAction(@PathVariable AuditAction action) {
        log.info("Obteniendo auditoría de acción: {}", action);
        List<AuditLog> logs = auditService.getAuditTrailByAction(action);
        return ResponseEntity.ok(ApiResponseDto.success(logs));
    }

    /**
     * Retrieves audit logs within a specified date range.
     *
     * @param startDate The start date of the range.
     * @param endDate   The end date of the range.
     * @return ResponseEntity containing a list of audit logs.
     */
    @GetMapping("/date-range")
    @Operation(summary = "Auditoría por rango de fechas", description = "Obtiene logs en un período de tiempo")
    public ResponseEntity<ApiResponseDto<List<AuditLog>>> getAuditTrailByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        log.info("Obteniendo auditoría desde {} hasta {}", startDate, endDate);
        List<AuditLog> logs = auditService.getAuditTrailByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponseDto.success(logs));
    }

    /**
     * Retrieves critical audit logs.
     *
     * @return ResponseEntity containing a list of critical audit logs.
     */
    @GetMapping("/critical")
    @Operation(summary = "Logs críticos", description = "Obtiene todos los logs marcados como críticos")
    public ResponseEntity<ApiResponseDto<List<AuditLog>>> getCriticalAuditLogs() {
        log.info("Obteniendo logs críticos");
        List<AuditLog> logs = auditService.getCriticalAuditLogs();
        return ResponseEntity.ok(ApiResponseDto.success(logs));
    }

    /**
     * Retrieves security-related audit logs.
     *
     * @return ResponseEntity containing a list of security audit logs.
     */
    @GetMapping("/security")
    @Operation(summary = "Logs de seguridad", description = "Obtiene logs relacionados con seguridad")
    public ResponseEntity<ApiResponseDto<List<AuditLog>>> getSecurityAuditLogs() {
        log.info("Obteniendo logs de seguridad");
        List<AuditLog> logs = auditService.getSecurityAuditLogs();
        return ResponseEntity.ok(ApiResponseDto.success(logs));
    }

    /**
     * Retrieves voting-related audit logs.
     *
     * @return ResponseEntity containing a list of voting audit logs.
     */
    @GetMapping("/voting")
    @Operation(summary = "Logs de votación", description = "Obtiene logs relacionados con votación")
    public ResponseEntity<ApiResponseDto<List<AuditLog>>> getVotingAuditLogs() {
        log.info("Obteniendo logs de votación");
        List<AuditLog> logs = auditService.getVotingAuditLogs();
        return ResponseEntity.ok(ApiResponseDto.success(logs));
    }

    /**
     * Generates an audit report for a specified date range.
     *
     * @param startDate The start date.
     * @param endDate   The end date.
     * @return ResponseEntity containing the generated audit report.
     */
    @GetMapping("/report")
    @Operation(summary = "Generar reporte", description = "Genera un reporte de auditoría")
    public ResponseEntity<ApiResponseDto<AuditService.AuditReport>> generateAuditReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        log.info("Generando reporte de auditoría desde {} hasta {}", startDate, endDate);
        AuditService.AuditReport report = auditService.generateAuditReport(startDate, endDate);
        return ResponseEntity.ok(ApiResponseDto.success(report));
    }
}
