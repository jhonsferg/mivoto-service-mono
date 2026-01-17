package pe.com.mivoto.service.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.mivoto.service.datastructures.implementations.CandidateSearchTree;
import pe.com.mivoto.service.datastructures.implementations.ElectionGraph;
import pe.com.mivoto.service.datastructures.implementations.VoteQueue;
import pe.com.mivoto.service.datastructures.implementations.VoteRecordList;
import pe.com.mivoto.service.presentation.dto.response.ApiResponse;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
@Tag(name = "Health", description = "Endpoints de salud del sistema")
public class HealthController {
    private final VoteQueue voteQueue;
    private final VoteRecordList voteRecordList;
    private final CandidateSearchTree candidateSearchTree;
    private final ElectionGraph electionGraph;

    @GetMapping
    @Operation(summary = "Health check", description = "Verifica el estado del sistema")
    public ResponseEntity<ApiResponse<HealthStatus>> healthCheck() {
        HealthStatus status = HealthStatus.builder()
                .status("UP")
                .timestamp(LocalDateTime.now())
                .database("UP")
                .dataStructures(getDataStructuresStatus())
                .build();
        return ResponseEntity.ok(ApiResponse.success(status));
    }

    @GetMapping("/detailed")
    @Operation(summary = "Health check detallado", description = "Verifica el estado detallado del sistema")
    public ResponseEntity<ApiResponse<DetailedHealthStatus>> detailedHealthCheck() {
        DetailedHealthStatus status = DetailedHealthStatus.builder()
                .status("UP")
                .timestamp(LocalDateTime.now())
                .voteQueueSize(voteQueue.size())
                .voteRecordsCount(voteRecordList.size())
                .candidateTreeSize(candidateSearchTree.size())
                .candidateTreeHeight(candidateSearchTree.height())
                .candidateTreeBalanced(candidateSearchTree.isBalanced())
                .electionGraphStats(electionGraph.getStatistics())
                .build();
        return ResponseEntity.ok(ApiResponse.success(status));
    }

    private DataStructuresStatus getDataStructuresStatus() {
        return DataStructuresStatus.builder()
                .voteQueue("UP")
                .voteRecordList("UP")
                .candidateSearchTree("UP")
                .electionGraph("UP")
                .build();
    }

    @Data
    @Builder
    public static class HealthStatus {
        private String status;
        private LocalDateTime timestamp;
        private String database;
        private DataStructuresStatus dataStructures;
    }

    @Data
    @Builder
    public static class DataStructuresStatus {
        private String voteQueue;
        private String voteRecordList;
        private String candidateSearchTree;
        private String electionGraph;
    }

    @Data
    @Builder
    public static class DetailedHealthStatus {
        private String status;
        private LocalDateTime timestamp;
        private Integer voteQueueSize;
        private Integer voteRecordsCount;
        private Integer candidateTreeSize;
        private Integer candidateTreeHeight;
        private Boolean candidateTreeBalanced;
        private ElectionGraph.GraphStatistics electionGraphStats;
    }
}
