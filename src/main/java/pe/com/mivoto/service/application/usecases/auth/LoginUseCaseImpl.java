package pe.com.mivoto.service.application.usecases.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.AuthenticationService;
import pe.com.mivoto.service.domain.model.VotingSession;

@Component
@RequiredArgsConstructor
public class LoginUseCaseImpl {
    private final AuthenticationService authenticationService;

    public VotingSession execute(String username, String password, String ipAddress, String userAgent) {
        return this.authenticationService.authenticate(username, password, ipAddress, userAgent);
    }
}
