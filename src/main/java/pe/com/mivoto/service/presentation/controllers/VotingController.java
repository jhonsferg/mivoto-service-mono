package pe.com.mivoto.service.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.mivoto.service.application.usecases.voting.CastVoteUseCaseImpl;
import pe.com.mivoto.service.application.usecases.voting.CheckVotingStatusUseCaseImpl;
import pe.com.mivoto.service.application.usecases.voting.GetVotingHistoryUseCaseImpl;
import pe.com.mivoto.service.application.usecases.voting.VerifyVoteUseCaseImpl;
import pe.com.mivoto.service.domain.model.Vote;
import pe.com.mivoto.service.domain.model.VoteRecord;
import pe.com.mivoto.service.infrastructure.security.jwt.JwtTokenProvider;
import pe.com.mivoto.service.presentation.dto.request.VoteRequest;
import pe.com.mivoto.service.presentation.dto.response.ApiResponse;
import pe.com.mivoto.service.presentation.dto.response.VoteRecordDto;
import pe.com.mivoto.service.presentation.dto.response.VoteResponse;
import pe.com.mivoto.service.presentation.mappers.VotingDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
@Tag(name = "Voting", description = "Endpoints de votación")
@SecurityRequirement(name = "bearerAuth")
public class VotingController {
    private final CastVoteUseCaseImpl castVoteUseCase;
    private final VerifyVoteUseCaseImpl verifyVoteUseCase;
    private final GetVotingHistoryUseCaseImpl getVotingHistoryUseCase;
    private final CheckVotingStatusUseCaseImpl checkVotingStatusUseCase;
    private final VotingDtoMapper votingDtoMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    @PreAuthorize("hasAnyRole('VOTER', 'ADMIN')")
    @Operation(summary = "Emitir voto", description = "Registra un voto del usuario autenticado")
    public ResponseEntity<ApiResponse<VoteResponse>> castVote(@Valid @RequestBody VoteRequest request, @RequestHeader("Authorization") String authHeader, HttpServletRequest httpRequest) {
        Long userId = getUserIdFromToken(authHeader);
        String ipAddress = getClientIP(httpRequest);
        String userAgent = httpRequest.getHeader("User-Agent");
        log.info("Emitiendo voto - Usuario: {}, Elección: {}, Candidato: {}", userId, request.getElectionId(), request.getCandidateId());
        Vote vote = castVoteUseCase.execute(
                userId,
                request.getElectionId(),
                request.getCandidateId(),
                ipAddress,
                userAgent
        );
        VoteResponse response = votingDtoMapper.toVoteResponse(vote);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Voto registrado exitosamente", response));
    }

    @GetMapping("/verify/{voteHash}")
    @Operation(summary = "Verificar voto", description = "Verifica un voto usando su hash único")
    public ResponseEntity<ApiResponse<VoteRecordDto>> verifyVote(@PathVariable String voteHash) {
        log.info("Verificando voto con hash: {}", voteHash);
        VoteRecord record = verifyVoteUseCase.execute(voteHash);
        VoteRecordDto response = votingDtoMapper.toVoteRecordDto(record);
        return ResponseEntity.ok(ApiResponse.success("Voto verificado", response));
    }

    @GetMapping("/history")
    @PreAuthorize("hasAnyRole('VOTER', 'ADMIN')")
    @Operation(summary = "Obtener historial", description = "Obtiene el historial de votos del usuario")
    public ResponseEntity<ApiResponse<List<VoteRecordDto>>> getVotingHistory(@RequestHeader("Authorization") String authHeader) {
        Long userId = getUserIdFromToken(authHeader);
        log.info("Obteniendo historial de votos para usuario: {}", userId);
        List<VoteRecord> records = getVotingHistoryUseCase.execute(userId);
        List<VoteRecordDto> response = records.stream()
                .map(votingDtoMapper::toVoteRecordDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/status/{electionId}")
    @PreAuthorize("hasAnyRole('VOTER', 'ADMIN')")
    @Operation(summary = "Verificar estado de voto", description = "Verifica si el usuario ya votó en una elección")
    public ResponseEntity<ApiResponse<Boolean>> checkVotingStatus(@PathVariable Long electionId, @RequestHeader("Authorization") String authHeader) {
        Long userId = getUserIdFromToken(authHeader);
        log.debug("Verificando estado de voto - Usuario: {}, Elección: {}", userId, electionId);
        boolean hasVoted = checkVotingStatusUseCase.execute(userId, electionId);
        return ResponseEntity.ok(ApiResponse.success(hasVoted));
    }

    @GetMapping("/election/{electionId}/count")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    @Operation(summary = "Contar votos", description = "Cuenta los votos totales de una elección")
    public ResponseEntity<ApiResponse<Long>> countVotesByElection(@PathVariable Long electionId) {
        return ResponseEntity.ok(ApiResponse.success(0L));
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

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
