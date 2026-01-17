package pe.com.mivoto.service.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.mivoto.service.application.usecases.auth.LoginUseCaseImpl;
import pe.com.mivoto.service.application.usecases.auth.LogoutUseCaseImpl;
import pe.com.mivoto.service.application.usecases.auth.RefreshTokenUseCaseImpl;
import pe.com.mivoto.service.domain.model.User;
import pe.com.mivoto.service.domain.model.VotingSession;
import pe.com.mivoto.service.presentation.dto.request.ChangePasswordRequest;
import pe.com.mivoto.service.presentation.dto.request.LoginRequest;
import pe.com.mivoto.service.presentation.dto.request.RefreshTokenRequest;
import pe.com.mivoto.service.presentation.dto.response.ApiResponse;
import pe.com.mivoto.service.presentation.dto.response.LoginResponse;
import pe.com.mivoto.service.presentation.mappers.AuthDtoMapper;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints de autenticación")
public class AuthController {
    private final LoginUseCaseImpl loginUseCase;
    private final LogoutUseCaseImpl logoutUseCase;
    private final RefreshTokenUseCaseImpl refreshTokenUseCase;
    private final AuthDtoMapper authDtoMapper;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y retorna tokens JWT")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        log.info("Login request para usuario: {}", request.getUsername());
        String ipAddress = getClientIP(httpRequest);
        String userAgent = httpRequest.getHeader("User-Agent");
        VotingSession session = loginUseCase.execute(request.getUsername(), request.getPassword(), ipAddress, userAgent);
        User user = getUserFromSession(session);
        LoginResponse response = authDtoMapper.toLoginResponse(session, user);
        return ResponseEntity.ok(ApiResponse.success("Login exitoso", response));
    }

    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión", description = "Invalida la sesión actual del usuario")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestHeader("Authorization") String authHeader) {
        String token = extractToken(authHeader);
        logoutUseCase.execute(token);
        return ResponseEntity.ok(ApiResponse.success("Sesión cerrada exitosamente", null));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refrescar token", description = "Genera un nuevo access token usando el refresh token")
    public ResponseEntity<ApiResponse<LoginResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        VotingSession session = refreshTokenUseCase.execute(request.getRefreshToken());
        User user = getUserFromSession(session);
        LoginResponse response = authDtoMapper.toLoginResponse(session, user);
        return ResponseEntity.ok(ApiResponse.success("Token refrescado", response));
    }

    @PostMapping("/change-password")
    @Operation(summary = "Cambiar contraseña", description = "Cambia la contraseña del usuario actual")
    public ResponseEntity<ApiResponse<Void>> changePassword(@Valid @RequestBody ChangePasswordRequest request, @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(ApiResponse.success("Contraseña cambiada exitosamente", null));
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return authHeader;
    }

    private User getUserFromSession(VotingSession session) {
        return User.builder().build();
    }
}
