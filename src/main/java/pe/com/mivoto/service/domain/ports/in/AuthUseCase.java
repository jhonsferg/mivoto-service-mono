package pe.com.mivoto.service.domain.ports.in;

import pe.com.mivoto.service.domain.model.User;
import pe.com.mivoto.service.domain.model.VotingSession;

/**
 * Input port for authentication and session management operations.
 * Handles user login, logout, token refresh, and security validations.
 */
public interface AuthUseCase {
    /**
     * Authenticates a user and establishes a new session.
     *
     * @param username The identifier (e.g., document number).
     * @param password The raw password.
     * @return A VotingSession containing access and refresh tokens.
     */
    VotingSession login(String username, String password);

    /**
     * Ends a user session and invalidates the associated token.
     *
     * @param sessionToken The access token to invalidate.
     */
    void logout(String sessionToken);

    /**
     * Obtains a new access token using a valid refresh token.
     *
     * @param refreshToken The refresh token.
     * @return A new VotingSession.
     */
    VotingSession refreshToken(String refreshToken);

    /**
     * Verifies if an access token is valid and not expired.
     *
     * @param sessionToken The access token to validate.
     * @return true if valid, false otherwise.
     */
    boolean validateToken(String sessionToken);

    /**
     * Retrieves the user associated with a specific session token.
     *
     * @param sessionToken The access token.
     * @return The User object.
     */
    User getUserFromToken(String sessionToken);

    /**
     * Updates the user's password after verifying the old one.
     *
     * @param userId      The user ID.
     * @param oldPassword The current password.
     * @param newPassword The new password.
     */
    void changePassword(Long userId, String oldPassword, String newPassword);

    /**
     * Checks if a user has an active authenticated session.
     *
     * @param userId The user ID.
     * @return true if currently authenticated.
     */
    boolean isAuthenticated(Long userId);
}