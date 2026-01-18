package pe.com.mivoto.service.application.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.mivoto.service.application.services.AuthenticationService;
import pe.com.mivoto.service.application.usecases.auth.LoginUseCaseImpl;
import pe.com.mivoto.service.application.usecases.auth.LogoutUseCaseImpl;
import pe.com.mivoto.service.application.usecases.auth.ValidateSessionUseCaseImpl;
import pe.com.mivoto.service.domain.model.User;
import pe.com.mivoto.service.domain.model.VotingSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test suite for Authentication Use Cases.
 * Covers login, logout, and session validation scenarios.
 */
@ExtendWith(MockitoExtension.class)
class AuthUseCasesTest {

    @Mock
    private AuthenticationService authenticationService;

    private LoginUseCaseImpl loginUseCase;
    private LogoutUseCaseImpl logoutUseCase;
    private ValidateSessionUseCaseImpl validateSessionUseCase;

    /**
     * Sets up the test environment by initializing use cases with mocked services.
     */
    @BeforeEach
    void setUp() {
        loginUseCase = new LoginUseCaseImpl(authenticationService);
        logoutUseCase = new LogoutUseCaseImpl(authenticationService);
        validateSessionUseCase = new ValidateSessionUseCaseImpl(authenticationService);
    }

    /**
     * Tests the execution of the login use case.
     * Verifies that the authentication service is called with correct credentials.
     */
    @Test
    @DisplayName("Should successfully authenticate user and return session")
    void testLoginExecution() {
        String username = "user@example.com";
        String password = "password";
        String ip = "127.0.0.1";
        String agent = "Mozilla/5.0";
        VotingSession expectedSession = new VotingSession();

        when(authenticationService.authenticate(username, password, ip, agent)).thenReturn(expectedSession);

        VotingSession result = loginUseCase.execute(username, password, ip, agent);

        assertNotNull(result);
        assertEquals(expectedSession, result);
        verify(authenticationService).authenticate(username, password, ip, agent);
    }

    /**
     * Tests the execution of the logout use case.
     * Verifies that the authentication service is called to invalidate the token.
     */
    @Test
    @DisplayName("Should successfully execute logout")
    void testLogoutExecution() {
        String token = "valid-token";

        doNothing().when(authenticationService).logout(token);

        logoutUseCase.execute(token);

        verify(authenticationService).logout(token);
    }

    /**
     * Tests valid session validation.
     * Verifies that the service returns true for a valid token.
     */
    @Test
    @DisplayName("Should validate valid session token")
    void testValidateSessionExecution_Valid() {
        String token = "valid-token";
        when(authenticationService.validateToken(token)).thenReturn(true);

        boolean isValid = validateSessionUseCase.execute(token);

        assertTrue(isValid);
        verify(authenticationService).validateToken(token);
    }

    /**
     * Tests invalid session validation.
     * Verifies that the service returns false for an invalid token.
     */
    @Test
    @DisplayName("Should invalidate invalid session token")
    void testValidateSessionExecution_Invalid() {
        String token = "invalid-token";
        when(authenticationService.validateToken(token)).thenReturn(false);

        boolean isValid = validateSessionUseCase.execute(token);

        assertFalse(isValid);
        verify(authenticationService).validateToken(token);
    }

    /**
     * Tests retrieving user from session token.
     */
    @Test
    @DisplayName("Should retrieve user from session token")
    void testGetUserFromSession() {
        String token = "valid-token";
        User expectedUser = User.builder().email("test@example.com").build();
        when(authenticationService.getUserFromToken(token)).thenReturn(expectedUser);

        User result = validateSessionUseCase.getUserFromSession(token);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(authenticationService).getUserFromToken(token);
    }
}
