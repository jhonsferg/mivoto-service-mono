package pe.com.mivoto.service.application.usecases.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.AuthenticationService;
import pe.com.mivoto.service.domain.model.VotingSession;

/**
 * Use case implementation for refreshing session tokens.
 * Wraps {@link AuthenticationService#refreshToken} logic.
 */
@Component
@RequiredArgsConstructor
public class RefreshTokenUseCaseImpl {
    private final AuthenticationService authenticationService;

    /**
     * Executes the refresh token use case.
     *
     * @param refreshToken The refresh token.
     * @return The new VotingSession.
     */
    public VotingSession execute(String refreshToken) {
        return this.authenticationService.refreshToken(refreshToken);
    }
}
