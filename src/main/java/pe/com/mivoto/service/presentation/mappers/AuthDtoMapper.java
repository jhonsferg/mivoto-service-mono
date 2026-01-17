package pe.com.mivoto.service.presentation.mappers;

import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.User;
import pe.com.mivoto.service.domain.model.VotingSession;
import pe.com.mivoto.service.presentation.dto.response.LoginResponse;
import pe.com.mivoto.service.presentation.dto.response.UserDto;

@Component
public class AuthDtoMapper {
    public LoginResponse toLoginResponse(VotingSession session, User user) {
        return LoginResponse.builder()
                .accessToken(session.getSessionToken())
                .refreshToken(session.getRefreshToken())
                .tokenType("Bearer")
                .expiresAt(session.getExpiresAt())
                .user(toUserDto(user))
                .build();
    }

    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .documentNumber(user.getDocumentNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .active(user.getActive())
                .lastLogin(user.getLastLogin())
                .build();
    }
}
