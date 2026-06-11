package pe.com.mivoto.service.application.services;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import pe.com.mivoto.service.domain.model.AuditLog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelExportService {

    private final StatisticsService statisticsService;
    private final AuditService auditService;

    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public byte[] generateElectionResultsReport(Long electionId) {
        log.info("Generando Excel de resultados para elección {}", electionId);
        StatisticsService.ElectionStatistics electionStats = statisticsService.getElectionStatistics(electionId);
        List<StatisticsService.CandidateStatistics> candidateStats = statisticsService.getCandidateStatistics(electionId);
        Map<Integer, Long> participation = statisticsService.getVotingParticipationByHour(electionId);

        try (Workbook wb = new XSSFWorkbook()) {
            buildResultsSheet(wb, wb.createSheet("Resultados"), electionStats, candidateStats);
            buildParticipationSheet(wb, wb.createSheet("Participación por Hora"), participation);
            return toBytes(wb);
        } catch (IOException e) {
            throw new RuntimeException("Error generando reporte de resultados", e);
        }
    }

    public byte[] generateAuditReport(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Generando Excel de auditoría {} - {}", startDate, endDate);
        List<AuditLog> logs = auditService.getAuditTrailByDateRange(startDate, endDate);

        try (Workbook wb = new XSSFWorkbook()) {
            buildAuditLogsSheet(wb, wb.createSheet("Logs de Auditoría"), logs);
            buildAuditSummarySheet(wb, wb.createSheet("Resumen"), logs, startDate, endDate);
            return toBytes(wb);
        } catch (IOException e) {
            throw new RuntimeException("Error generando reporte de auditoría", e);
        }
    }

    public byte[] generateSystemStatisticsReport() {
        log.info("Generando Excel de estadísticas del sistema");
        StatisticsService.SystemStatistics stats = statisticsService.getSystemStatistics();

        try (Workbook wb = new XSSFWorkbook()) {
            buildSystemStatisticsSheet(wb, wb.createSheet("Estadísticas del Sistema"), stats);
            return toBytes(wb);
        } catch (IOException e) {
            throw new RuntimeException("Error generando reporte de estadísticas", e);
        }
    }

    // --- Sheet builders ---

    private void buildResultsSheet(Workbook wb, Sheet sheet,
            StatisticsService.ElectionStatistics electionStats,
            List<StatisticsService.CandidateStatistics> candidates) {

        CellStyle headerStyle = createHeaderStyle(wb);
        CellStyle titleStyle = createTitleStyle(wb);
        CellStyle percentStyle = createPercentStyle(wb);
        CellStyle altRowStyle = createAltRowStyle(wb);

        int row = 0;

        // Title
        Cell titleCell = sheet.createRow(row++).createCell(0);
        titleCell.setCellValue("Resultados: " + electionStats.electionTitle());
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
        row++;

        // Summary
        Row summaryRow = sheet.createRow(row++);
        summaryRow.createCell(0).setCellValue("Total Votos:");
        summaryRow.createCell(1).setCellValue(electionStats.totalVotes());
        summaryRow.createCell(2).setCellValue("Candidatos:");
        summaryRow.createCell(3).setCellValue(electionStats.totalCandidates());
        row++;

        // Header
        ImmutableList<String> headers = ImmutableList.of("N° Ballot", "Candidato", "Partido", "Votos", "Porcentaje");
        Row headerRow = sheet.createRow(row++);
        for (int i = 0; i < headers.size(); i++) {
            styledCell(headerRow, i, headers.get(i), headerStyle);
        }

        // Data rows
        boolean alt = false;
        for (StatisticsService.CandidateStatistics cs : candidates) {
            Row dataRow = sheet.createRow(row++);
            CellStyle rowStyle = alt ? altRowStyle : null;

            setCell(dataRow, 0, cs.candidateId(), rowStyle);
            setCell(dataRow, 1, cs.candidateName(), rowStyle);
            setCell(dataRow, 2, cs.party() != null ? cs.party() : "-", rowStyle);
            setCell(dataRow, 3, cs.votes(), rowStyle);

            Cell pctCell = dataRow.createCell(4);
            pctCell.setCellValue(cs.percentage() / 100.0);
            pctCell.setCellStyle(percentStyle);

            alt = !alt;
        }

        for (int i = 0; i <= 4; i++) sheet.autoSizeColumn(i);
    }

    private void buildParticipationSheet(Workbook wb, Sheet sheet, Map<Integer, Long> participation) {
        CellStyle headerStyle = createHeaderStyle(wb);
        int row = 0;

        Row headerRow = sheet.createRow(row++);
        styledCell(headerRow, 0, "Hora (0-23)", headerStyle);
        styledCell(headerRow, 1, "Votos", headerStyle);

        participation.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .forEach(entry -> {
                    Row dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                    dataRow.createCell(0).setCellValue(entry.getKey());
                    dataRow.createCell(1).setCellValue(entry.getValue());
                });

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
    }

    private void buildAuditLogsSheet(Workbook wb, Sheet sheet, List<AuditLog> logs) {
        CellStyle headerStyle = createHeaderStyle(wb);
        int row = 0;

        ImmutableList<String> headers = ImmutableList.of(
                "ID", "Usuario ID", "Acción", "Entidad", "Entidad ID",
                "Descripción", "IP", "Timestamp");
        Row headerRow = sheet.createRow(row++);
        for (int i = 0; i < headers.size(); i++) {
            styledCell(headerRow, i, headers.get(i), headerStyle);
        }

        for (AuditLog al : logs) {
            Row dataRow = sheet.createRow(row++);
            dataRow.createCell(0).setCellValue(al.getId() != null ? al.getId() : 0L);
            dataRow.createCell(1).setCellValue(al.getUserId() != null ? al.getUserId() : 0L);
            dataRow.createCell(2).setCellValue(al.getAction() != null ? al.getAction().name() : "");
            dataRow.createCell(3).setCellValue(al.getEntity() != null ? al.getEntity() : "");
            dataRow.createCell(4).setCellValue(al.getEntityId() != null ? al.getEntityId() : 0L);
            dataRow.createCell(5).setCellValue(al.getDescription() != null ? al.getDescription() : "");
            dataRow.createCell(6).setCellValue(al.getIpAddress() != null ? al.getIpAddress() : "");
            dataRow.createCell(7).setCellValue(
                    al.getTimestamp() != null ? al.getTimestamp().format(DISPLAY_FORMAT) : "");
        }

        for (int i = 0; i <= 7; i++) sheet.autoSizeColumn(i);
    }

    private void buildAuditSummarySheet(Workbook wb, Sheet sheet, List<AuditLog> logs,
            LocalDateTime startDate, LocalDateTime endDate) {

        CellStyle headerStyle = createHeaderStyle(wb);
        CellStyle titleStyle = createTitleStyle(wb);
        int row = 0;

        Cell titleCell = sheet.createRow(row++).createCell(0);
        titleCell.setCellValue("Resumen de Auditoría");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        row++;

        Row periodRow = sheet.createRow(row++);
        periodRow.createCell(0).setCellValue("Período:");
        periodRow.createCell(1).setCellValue(
                startDate.format(DISPLAY_FORMAT) + " — " + endDate.format(DISPLAY_FORMAT));

        addKV(sheet, row++, "Total Acciones:", logs.size());
        addKV(sheet, row++, "Acciones Críticas:", (int) logs.stream().filter(AuditLog::isCritical).count());
        addKV(sheet, row++, "Incidentes de Seguridad:", (int) logs.stream().filter(AuditLog::isSecurityRelated).count());
        addKV(sheet, row++, "Eventos de Votación:", (int) logs.stream().filter(AuditLog::isVoteRelated).count());
        row++;

        Row breakdownHeader = sheet.createRow(row++);
        styledCell(breakdownHeader, 0, "Tipo de Acción", headerStyle);
        styledCell(breakdownHeader, 1, "Cantidad", headerStyle);

        Map<String, Long> actionCounts = logs.stream()
                .filter(l -> l.getAction() != null)
                .collect(Collectors.groupingBy(l -> l.getAction().name(), Collectors.counting()));

        actionCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry -> {
                    Row dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                    dataRow.createCell(0).setCellValue(entry.getKey());
                    dataRow.createCell(1).setCellValue(entry.getValue());
                });

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
    }

    private void buildSystemStatisticsSheet(Workbook wb, Sheet sheet,
            StatisticsService.SystemStatistics stats) {

        CellStyle headerStyle = createHeaderStyle(wb);
        CellStyle titleStyle = createTitleStyle(wb);
        int row = 0;

        Cell titleCell = sheet.createRow(row++).createCell(0);
        titleCell.setCellValue("Estadísticas del Sistema MiVoto");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        row++;

        Row genRow = sheet.createRow(row++);
        genRow.createCell(0).setCellValue("Generado:");
        genRow.createCell(1).setCellValue(stats.generatedAt().format(DISPLAY_FORMAT));
        row++;

        Row headerRow = sheet.createRow(row++);
        styledCell(headerRow, 0, "Métrica", headerStyle);
        styledCell(headerRow, 1, "Valor", headerStyle);

        ImmutableList<Object[]> metrics = ImmutableList.of(
                new Object[]{"Total Usuarios Registrados", stats.totalUsers()},
                new Object[]{"Total Elecciones Creadas", stats.totalElections()},
                new Object[]{"Elecciones Activas", stats.activeElections()},
                new Object[]{"Total Votos Emitidos", stats.totalVotes()});

        for (Object[] metric : metrics) {
            Row dataRow = sheet.createRow(row++);
            dataRow.createCell(0).setCellValue((String) metric[0]);
            dataRow.createCell(1).setCellValue(((Long) metric[1]).doubleValue());
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
    }

    // --- Style helpers ---

    private CellStyle createHeaderStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        return style;
    }

    private CellStyle createTitleStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private CellStyle createPercentStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setDataFormat(wb.createDataFormat().getFormat("0.00%"));
        return style;
    }

    private CellStyle createAltRowStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private void styledCell(Row row, int col, String value, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value);
        if (style != null) cell.setCellStyle(style);
    }

    private void setCell(Row row, int col, Object value, CellStyle style) {
        Cell cell = row.createCell(col);
        if (value instanceof Long v) cell.setCellValue(v.doubleValue());
        else if (value instanceof String s) cell.setCellValue(s);
        if (style != null) cell.setCellStyle(style);
    }

    private void addKV(Sheet sheet, int rowIdx, String key, int value) {
        Row row = sheet.createRow(rowIdx);
        row.createCell(0).setCellValue(key);
        row.createCell(1).setCellValue(value);
    }

    private byte[] toBytes(Workbook wb) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            wb.write(out);
            return out.toByteArray();
        }
    }
}
