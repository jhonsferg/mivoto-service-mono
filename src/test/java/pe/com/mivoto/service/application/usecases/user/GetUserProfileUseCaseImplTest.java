package pe.com.mivoto.service.application.usecases.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.mivoto.service.domain.model.User;
import pe.com.mivoto.service.domain.ports.in.AuthUseCase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserProfileUseCaseImplTest {

    @Mock
    private AuthUseCase authUseCase;

    private GetUserProfileUseCaseImpl getUserProfileUseCase;

    @BeforeEach
    void setUp() {
        getUserProfileUseCase = new GetUserProfileUseCaseImpl(authUseCase);
    }

    @Test
    @DisplayName("Should return user from DB via authUseCase")
    void testExecute_ReturnsFromDB() {
        String token = "valid-token";
        User dbUser = User.builder().id(1L).email("user@example.com").build();

        when(authUseCase.getUserFromToken(token)).thenReturn(dbUser);

        User result = getUserProfileUseCase.execute(token);

        assertNotNull(result);
        assertEquals(dbUser, result);
        verify(authUseCase).getUserFromToken(token);
    }
}
