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
import pe.com.mivoto.service.presentation.dto.response.ApiResponse;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
@Tag(name = "Audit", description = "Endpoints de auditoría")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
public class AuditController {
    private final AuditService auditService;

    @GetMapping("/user/{userId}")
    @Operation(summary = "Auditoría por usuario", description = "Obtiene logs de auditoría de un usuario")
    public ResponseEntity<ApiResponse<List<AuditLog>>> getAuditTrailByUser(@PathVariable Long userId) {
        log.info("Obteniendo auditoría de usuario: {}", userId);
        List<AuditLog> logs = auditService.getAuditTrailByUser(userId);
        return ResponseEntity.ok(ApiResponse.success(logs));
    }

    @GetMapping("/entity/{entity}/{entityId}")
    @Operation(summary = "Auditoría por entidad", description = "Obtiene logs de auditoría de una entidad")
    public ResponseEntity<ApiResponse<List<AuditLog>>> getAuditTrailByEntity(@PathVariable String entity, @PathVariable Long entityId) {
        log.info("Obteniendo auditoría de entidad: {}/{}", entity, entityId);
        List<AuditLog> logs = auditService.getAuditTrailByEntity(entity, entityId);
        return ResponseEntity.ok(ApiResponse.success(logs));
    }

    @GetMapping("/action/{action}")
    @Operation(summary = "Auditoría por acción", description = "Obtiene logs de auditoría por tipo de acción")
    public ResponseEntity<ApiResponse<List<AuditLog>>> getAuditTrailByAction(@PathVariable AuditAction action) {
        log.info("Obteniendo auditoría de acción: {}", action);
        List<AuditLog> logs = auditService.getAuditTrailByAction(action);
        return ResponseEntity.ok(ApiResponse.success(logs));
    }

    @GetMapping("/date-range")
    @Operation(summary = "Auditoría por rango de fechas", description = "Obtiene logs en un período de tiempo")
    public ResponseEntity<ApiResponse<List<AuditLog>>> getAuditTrailByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        log.info("Obteniendo auditoría desde {} hasta {}", startDate, endDate);
        List<AuditLog> logs = auditService.getAuditTrailByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(logs));
    }

    @GetMapping("/critical")
    @Operation(summary = "Logs críticos", description = "Obtiene todos los logs marcados como críticos")
    public ResponseEntity<ApiResponse<List<AuditLog>>> getCriticalAuditLogs() {
        log.info("Obteniendo logs críticos");
        List<AuditLog> logs = auditService.getCriticalAuditLogs();
        return ResponseEntity.ok(ApiResponse.success(logs));
    }

    @GetMapping("/security")
    @Operation(summary = "Logs de seguridad", description = "Obtiene logs relacionados con seguridad")
    public ResponseEntity<ApiResponse<List<AuditLog>>> getSecurityAuditLogs() {
        log.info("Obteniendo logs de seguridad");
        List<AuditLog> logs = auditService.getSecurityAuditLogs();
        return ResponseEntity.ok(ApiResponse.success(logs));
    }

    @GetMapping("/voting")
    @Operation(summary = "Logs de votación", description = "Obtiene logs relacionados con votación")
    public ResponseEntity<ApiResponse<List<AuditLog>>> getVotingAuditLogs() {
        log.info("Obteniendo logs de votación");
        List<AuditLog> logs = auditService.getVotingAuditLogs();
        return ResponseEntity.ok(ApiResponse.success(logs));
    }

    @GetMapping("/report")
    @Operation(summary = "Generar reporte", description = "Genera un reporte de auditoría")
    public ResponseEntity<ApiResponse<AuditService.AuditReport>> generateAuditReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        log.info("Generando reporte de auditoría desde {} hasta {}", startDate, endDate);
        AuditService.AuditReport report = auditService.generateAuditReport(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(report));
    }
}
