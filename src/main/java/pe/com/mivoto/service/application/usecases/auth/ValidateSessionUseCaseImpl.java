package pe.com.mivoto.service.application.usecases.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.AuthenticationService;
import pe.com.mivoto.service.domain.model.User;

@Component
@RequiredArgsConstructor
public class ValidateSessionUseCaseImpl {
    private final AuthenticationService authenticationService;

    public boolean execute(String sessionToken) {
        return this.authenticationService.validateToken(sessionToken);
    }

    public User getUserFromSession(String sessionToken) {
        return this.authenticationService.getUserFromToken(sessionToken);
    }
}
