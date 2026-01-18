package pe.com.mivoto.service.presentation.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.com.mivoto.service.application.usecases.auth.LoginUseCaseImpl;
import pe.com.mivoto.service.application.usecases.auth.LogoutUseCaseImpl;
import pe.com.mivoto.service.application.usecases.auth.RefreshTokenUseCaseImpl;
import pe.com.mivoto.service.domain.model.User;
import pe.com.mivoto.service.domain.model.VotingSession;
import pe.com.mivoto.service.presentation.dto.request.LoginRequest;
import pe.com.mivoto.service.presentation.dto.request.RefreshTokenRequest;
import pe.com.mivoto.service.presentation.dto.response.ApiResponse;
import pe.com.mivoto.service.presentation.dto.response.LoginResponse;
import pe.com.mivoto.service.presentation.mappers.AuthDtoMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Test suite for AuthController.
 * Verifies the endpoints for login, logout, and token refresh.
 */
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private LoginUseCaseImpl loginUseCase;
    @Mock
    private LogoutUseCaseImpl logoutUseCase;
    @Mock
    private RefreshTokenUseCaseImpl refreshTokenUseCase;
    @Mock
    private AuthDtoMapper authDtoMapper;
    @Mock
    private HttpServletRequest httpRequest;

    private AuthController authController;

    @BeforeEach
    void setUp() {
        authController = new AuthController(loginUseCase, logoutUseCase, refreshTokenUseCase, authDtoMapper);
    }

    /**
     * Tests the login endpoint.
     * Verifies that the use case is called and a valid response is returned.
     */
    @Test
    @DisplayName("Should successfully login user")
    void testLogin() {
        LoginRequest request = new LoginRequest();
        request.setUsername("user");
        request.setPassword("pass");

        when(httpRequest.getHeader("X-Forwarded-For")).thenReturn(null);
        when(httpRequest.getRemoteAddr()).thenReturn("127.0.0.1");
        when(httpRequest.getHeader("User-Agent")).thenReturn("TestAgent");

        VotingSession session = new VotingSession();
        when(loginUseCase.execute(anyString(), anyString(), anyString(), anyString())).thenReturn(session);

        User user = User.builder().build(); // Mocking result of private getUserFromSession (reflection might be needed
                                            // or ignored if private method is dummy)
        // Note: The controller calls a private method getUserFromSession which builds
        // an empty user.
        // We mock the mapper to return a response based on that.

        LoginResponse loginResponse = new LoginResponse();
        when(authDtoMapper.toLoginResponse(any(VotingSession.class), any(User.class))).thenReturn(loginResponse);

        ResponseEntity<ApiResponse<LoginResponse>> response = authController.login(request, httpRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(loginResponse, response.getBody().getData());
        verify(loginUseCase).execute("user", "pass", "127.0.0.1", "TestAgent");
    }

    /**
     * Tests the logout endpoint.
     */
    @Test
    @DisplayName("Should successfully logout user")
    void testLogout() {
        String authHeader = "Bearer valid-token";

        doNothing().when(logoutUseCase).execute("valid-token");

        ResponseEntity<ApiResponse<Void>> response = authController.logout(authHeader);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(logoutUseCase).execute("valid-token");
    }

    /**
     * Tests the refresh token endpoint.
     */
    @Test
    @DisplayName("Should successfully refresh token")
    void testRefreshToken() {
        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken("refresh-token");

        VotingSession session = new VotingSession();
        when(refreshTokenUseCase.execute("refresh-token")).thenReturn(session);

        LoginResponse loginResponse = new LoginResponse();
        when(authDtoMapper.toLoginResponse(any(VotingSession.class), any(User.class))).thenReturn(loginResponse);

        ResponseEntity<ApiResponse<LoginResponse>> response = authController.refreshToken(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(loginResponse, response.getBody().getData());
    }
}
