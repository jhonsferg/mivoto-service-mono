package pe.com.mivoto.service.application.usecases.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.mivoto.service.domain.model.User;
import pe.com.mivoto.service.domain.ports.in.AuthUseCase;
import pe.com.mivoto.service.infrastructure.persistence.redis.UserCacheRepository;
import pe.com.mivoto.service.infrastructure.security.jwt.JwtTokenProvider;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserProfileUseCaseImplTest {

    @Mock
    private AuthUseCase authUseCase;
    @Mock
    private UserCacheRepository userCacheRepository;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    private GetUserProfileUseCaseImpl getUserProfileUseCase;

    @BeforeEach
    void setUp() {
        getUserProfileUseCase = new GetUserProfileUseCaseImpl(authUseCase, userCacheRepository, jwtTokenProvider);
    }

    @Test
    @DisplayName("Should return user from cache when available")
    void testExecute_CacheHit() {
        String token = "valid-token";
        Long userId = 1L;
        User cachedUser = User.builder().id(userId).email("cached@example.com").build();

        when(jwtTokenProvider.getUserIdFromToken(token)).thenReturn(userId);
        when(userCacheRepository.findById(userId)).thenReturn(Optional.of(cachedUser));

        User result = getUserProfileUseCase.execute(token);

        assertNotNull(result);
        assertEquals(cachedUser, result);
        verify(userCacheRepository).findById(userId);
        verify(authUseCase, never()).getUserFromToken(anyString());
    }

    @Test
    @DisplayName("Should fetch from DB and cache when not in cache")
    void testExecute_CacheMiss() {
        String token = "valid-token";
        Long userId = 1L;
        User dbUser = User.builder().id(userId).email("db@example.com").build();

        when(jwtTokenProvider.getUserIdFromToken(token)).thenReturn(userId);
        when(userCacheRepository.findById(userId)).thenReturn(Optional.empty());
        when(authUseCase.getUserFromToken(token)).thenReturn(dbUser);

        User result = getUserProfileUseCase.execute(token);

        assertNotNull(result);
        assertEquals(dbUser, result);
        verify(userCacheRepository).findById(userId);
        verify(authUseCase).getUserFromToken(token);
        verify(userCacheRepository).save(dbUser);
    }
}
