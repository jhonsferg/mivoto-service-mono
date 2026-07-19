package pe.com.mivoto.service.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.com.mivoto.service.application.services.ExcelExportService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Generación y descarga de reportes en formato Excel")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
public class ReportController {

    private final ExcelExportService excelExportService;

    private static final String XLSX_MEDIA_TYPE =
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final DateTimeFormatter FILE_TS = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    @GetMapping("/elections/{electionId}/results")
    @Operation(
            summary = "Reporte de resultados de elección",
            description = "Descarga un archivo Excel con los resultados por candidato y participación por hora")
    public ResponseEntity<byte[]> electionResults(@PathVariable Long electionId) {
        log.info("Descargando reporte de resultados - elección {}", electionId);
        byte[] content = excelExportService.generateElectionResultsReport(electionId);
        String filename = String.format("resultados_eleccion_%d_%s.xlsx", electionId, LocalDateTime.now().format(FILE_TS));
        return xlsxResponse(content, filename);
    }

    @GetMapping("/audit")
    @Operation(
            summary = "Reporte de auditoría",
            description = "Descarga un archivo Excel con los logs de auditoría en el rango de fechas indicado")
    public ResponseEntity<byte[]> auditReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("Descargando reporte de auditoría {} - {}", startDate, endDate);
        byte[] content = excelExportService.generateAuditReport(startDate, endDate);
        String filename = String.format("auditoria_%s.xlsx", LocalDateTime.now().format(FILE_TS));
        return xlsxResponse(content, filename);
    }

    @GetMapping("/statistics")
    @Operation(
            summary = "Reporte de estadísticas del sistema",
            description = "Descarga un archivo Excel con las estadísticas globales del sistema")
    public ResponseEntity<byte[]> systemStatistics() {
        log.info("Descargando reporte de estadísticas del sistema");
        byte[] content = excelExportService.generateSystemStatisticsReport();
        String filename = String.format("estadisticas_%s.xlsx", LocalDateTime.now().format(FILE_TS));
        return xlsxResponse(content, filename);
    }

    private ResponseEntity<byte[]> xlsxResponse(byte[] content, String filename) {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(XLSX_MEDIA_TYPE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                .body(content);
    }
}
