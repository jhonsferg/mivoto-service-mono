package pe.com.mivoto.service.application.usecases.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.AuthenticationService;

/**
 * Use case implementation for user logout.
 * Wraps {@link AuthenticationService#logout} logic.
 */
@Component
@RequiredArgsConstructor
public class LogoutUseCaseImpl {
    private final AuthenticationService authenticationService;

    /**
     * Executes the logout use case.
     *
     * @param sessionToken The active session token.
     */
    public void execute(String sessionToken) {
        this.authenticationService.logout(sessionToken);
    }
}
