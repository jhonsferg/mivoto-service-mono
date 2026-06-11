package pe.com.mivoto.service.application.usecases.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.User;
import pe.com.mivoto.service.domain.ports.in.AuthUseCase;

/**
 * Use case implementation for retrieving the authenticated user's profile.
 */
@Component
@RequiredArgsConstructor
public class GetUserProfileUseCaseImpl {

    private final AuthUseCase authUseCase;

    /**
     * Executes the use case to get the user profile from a token.
     *
     * @param token The JWT access token.
     * @return The User domain object.
     */
    public User execute(String token) {
        return authUseCase.getUserFromToken(token);
    }
}
