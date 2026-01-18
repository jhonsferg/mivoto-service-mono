package pe.com.mivoto.service.application.usecases.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.AuthenticationService;
import pe.com.mivoto.service.domain.model.VotingSession;

/**
 * Use case implementation for user login.
 * Wraps {@link AuthenticationService#authenticate} logic.
 */
@Component
@RequiredArgsConstructor
public class LoginUseCaseImpl {
    private final AuthenticationService authenticationService;

    /**
     * Executes the login use case.
     *
     * @param username  The user's username.
     * @param password  The user's password.
     * @param ipAddress The IP address.
     * @param userAgent The User-Agent.
     * @return The active VotingSession.
     */
    public VotingSession execute(String username, String password, String ipAddress, String userAgent) {
        return this.authenticationService.authenticate(username, password, ipAddress, userAgent);
    }
}
