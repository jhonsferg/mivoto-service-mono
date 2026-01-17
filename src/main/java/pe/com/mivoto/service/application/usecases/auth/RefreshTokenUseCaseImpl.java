package pe.com.mivoto.service.application.usecases.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.AuthenticationService;
import pe.com.mivoto.service.domain.model.VotingSession;

@Component
@RequiredArgsConstructor
public class RefreshTokenUseCaseImpl {
    private final AuthenticationService authenticationService;

    public VotingSession execute(String refreshToken) {
        return this.authenticationService.refreshToken(refreshToken);
    }
}
