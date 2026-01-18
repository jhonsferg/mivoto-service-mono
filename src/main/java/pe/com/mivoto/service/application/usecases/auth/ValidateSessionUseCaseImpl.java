package pe.com.mivoto.service.application.usecases.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.AuthenticationService;
import pe.com.mivoto.service.domain.model.User;

/**
 * Use case implementation for validating sessions.
 * Wraps {@link AuthenticationService#validateToken} logic.
 */
@Component
@RequiredArgsConstructor
public class ValidateSessionUseCaseImpl {
    private final AuthenticationService authenticationService;

    /**
     * Validates a session token.
     *
     * @param sessionToken The session token.
     * @return true if valid, false otherwise.
     */
    public boolean execute(String sessionToken) {
        return this.authenticationService.validateToken(sessionToken);
    }

    /**
     * Retrieves the user associated with a session token.
     *
     * @param sessionToken The session token.
     * @return The User object.
     */
    public User getUserFromSession(String sessionToken) {
        return this.authenticationService.getUserFromToken(sessionToken);
    }
}
