package pe.com.mivoto.service.domain.ports.in;

import pe.com.mivoto.service.domain.model.User;
import pe.com.mivoto.service.domain.model.VotingSession;

public interface AuthUseCase {
    VotingSession login(String username, String password);

    void logout(String sessionToken);

    VotingSession refreshToken(String refreshToken);

    boolean validateToken(String sessionToken);

    User getUserFromToken(String sessionToken);

    void changePassword(Long userId, String oldPassword, String newPassword);

    boolean isAuthenticated(Long userId);
}