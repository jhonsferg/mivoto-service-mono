package pe.com.mivoto.service.application.usecases.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.User;
import pe.com.mivoto.service.domain.ports.in.AuthUseCase;
import pe.com.mivoto.service.infrastructure.persistence.redis.UserCacheRepository;
import pe.com.mivoto.service.infrastructure.security.jwt.JwtTokenProvider;

/**
 * Use case implementation for retrieving the authenticated user's profile.
 */
@Component
@RequiredArgsConstructor
public class GetUserProfileUseCaseImpl {

    private final AuthUseCase authUseCase;
    private final UserCacheRepository userCacheRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Executes the use case to get the user profile from a token.
     * Checks Redis cache first, then falls back to AuthUseCase (DB).
     *
     * @param token The JWT access token.
     * @return The User domain object.
     */
    public User execute(String token) {
        Long userId = jwtTokenProvider.getUserIdFromToken(token);

        return userCacheRepository.findById(userId)
                .orElseGet(() -> {
                    User user = authUseCase.getUserFromToken(token);
                    userCacheRepository.save(user);
                    return user;
                });
    }
}
