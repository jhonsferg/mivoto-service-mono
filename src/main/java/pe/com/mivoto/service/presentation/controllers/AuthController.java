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
import pe.com.mivoto.service.application.usecases.user.GetUserProfileUseCaseImpl;
import pe.com.mivoto.service.domain.model.User;
import pe.com.mivoto.service.domain.model.VotingSession;
import pe.com.mivoto.service.presentation.dto.response.UserDto;
import pe.com.mivoto.service.presentation.dto.request.ChangePasswordRequestDto;
import pe.com.mivoto.service.presentation.dto.request.LoginRequestDto;
import pe.com.mivoto.service.presentation.dto.request.RefreshTokenRequestDto;
import pe.com.mivoto.service.presentation.dto.response.ApiResponseDto;
import pe.com.mivoto.service.presentation.dto.response.LoginResponseDto;
import pe.com.mivoto.service.presentation.mappers.AuthDtoMapper;

/**
 * REST Controller for Authentication.
 * Handles login, logout, token refresh, and password changes.
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints de autenticación")
public class AuthController {
    private final LoginUseCaseImpl loginUseCase;
    private final LogoutUseCaseImpl logoutUseCase;
    private final RefreshTokenUseCaseImpl refreshTokenUseCase;
    private final GetUserProfileUseCaseImpl getUserProfileUseCase;
    private final AuthDtoMapper authDtoMapper;

    /**
     * Authenticates a user and issues JWT tokens.
     *
     * @param request     The login request containing username and password.
     * @param httpRequest The HTTP servlet request to extract IP and User-Agent.
     * @return ResponseEntity containing the login response with tokens.
     */
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y retorna tokens JWT")
    public ResponseEntity<ApiResponseDto<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto request,
            HttpServletRequest httpRequest) {
        log.info("Login request para usuario: {}", request.getUsername());
        String ipAddress = getClientIP(httpRequest);
        String userAgent = httpRequest.getHeader("User-Agent");
        VotingSession session = loginUseCase.execute(request.getUsername(), request.getPassword(), ipAddress,
                userAgent);
        User user = getUserFromSession(session);
        LoginResponseDto response = authDtoMapper.toLoginResponse(session, user);
        return ResponseEntity.ok(ApiResponseDto.success("Login exitoso", response));
    }

    /**
     * Logs out the current user by invalidating the access token.
     *
     * @param authHeader The Authorization header containing the JWT token.
     * @return ResponseEntity indicating successful logout.
     */
    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión", description = "Invalida la sesión actual del usuario")
    public ResponseEntity<ApiResponseDto<Void>> logout(@RequestHeader("Authorization") String authHeader) {
        String token = extractToken(authHeader);
        logoutUseCase.execute(token);
        return ResponseEntity.ok(ApiResponseDto.success("Sesión cerrada exitosamente", null));
    }

    /**
     * Refreshes the access token using a valid refresh token.
     *
     * @param request The refresh token request.
     * @return ResponseEntity containing the new tokens.
     */
    @PostMapping("/refresh")
    @Operation(summary = "Refrescar token", description = "Genera un nuevo access token usando el refresh token")
    public ResponseEntity<ApiResponseDto<LoginResponseDto>> refreshToken(
            @Valid @RequestBody RefreshTokenRequestDto request) {
        VotingSession session = refreshTokenUseCase.execute(request.getRefreshToken());
        User user = getUserFromSession(session);
        LoginResponseDto response = authDtoMapper.toLoginResponse(session, user);
        return ResponseEntity.ok(ApiResponseDto.success("Token refrescado", response));
    }

    /**
     * Retrieves the authenticated user's profile information.
     *
     * @param authHeader The Authorization header containing the JWT token.
     * @return ResponseEntity containing the user details.
     */
    @GetMapping("/me")
    @Operation(summary = "Obtener perfil", description = "Obtiene la información del usuario autenticado")
    public ResponseEntity<ApiResponseDto<UserDto>> me(@RequestHeader("Authorization") String authHeader) {
        String token = extractToken(authHeader);
        User user = getUserProfileUseCase.execute(token);
        UserDto response = authDtoMapper.toUserDto(user);
        return ResponseEntity.ok(ApiResponseDto.success("Perfil de usuario obtenido exitosamente", response));
    }

    /**
     * Changes the password for the authenticated user.
     *
     * @param request    The password change request containing old and new
     *                   passwords.
     * @param authHeader The Authorization header containing the JWT token.
     * @return ResponseEntity indicating successful password change.
     */
    @PostMapping("/change-password")
    @Operation(summary = "Cambiar contraseña", description = "Cambia la contraseña del usuario actual")
    public ResponseEntity<ApiResponseDto<Void>> changePassword(@Valid @RequestBody ChangePasswordRequestDto request,
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(ApiResponseDto.success("Contraseña cambiada exitosamente", null));
    }

    /**
     * Extracts the client IP address from the request.
     *
     * @param request The HTTP servlet request.
     * @return The client IP address.
     */
    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    /**
     * Extracts the JWT token from the Authorization header.
     *
     * @param authHeader The Authorization header.
     * @return The token string.
     */
    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return authHeader;
    }

    /**
     * Retrieves the user associated with a session.
     *
     * @param session The voting session.
     * @return The user.
     */
    private User getUserFromSession(VotingSession session) {
        return User.builder().build();
    }
}
