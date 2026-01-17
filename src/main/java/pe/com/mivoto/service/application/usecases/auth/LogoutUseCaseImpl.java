package pe.com.mivoto.service.application.usecases.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.application.services.AuthenticationService;

@Component
@RequiredArgsConstructor
public class LogoutUseCaseImpl {
    private final AuthenticationService authenticationService;

    public void execute(String sessionToken) {
        this.authenticationService.logout(sessionToken);
    }
}
