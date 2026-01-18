package pe.com.mivoto.service.presentation.mappers;

import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.User;
import pe.com.mivoto.service.domain.model.VotingSession;
import pe.com.mivoto.service.presentation.dto.response.LoginResponseDto;
import pe.com.mivoto.service.presentation.dto.response.UserDto;

/**
 * Mapper for Authentication DTOs.
 * Converts between authentication-related domain models and DTOs.
 */
@Component
public class AuthDtoMapper {

    /**
     * Converts VotingSession and User to LoginResponseDto.
     *
     * @param session The voting session containing token details.
     * @param user    The user details.
     * @return The LoginResponseDto containing tokens and user info.
     */
    public LoginResponseDto toLoginResponse(VotingSession session, User user) {
        return LoginResponseDto.builder()
                .accessToken(session.getSessionToken())
                .refreshToken(session.getRefreshToken())
                .tokenType("Bearer")
                .expiresAt(session.getExpiresAt())
                .user(toUserDto(user))
                .build();
    }

    /**
     * Converts User domain model to UserDto.
     *
     * @param user The User domain model.
     * @return The UserDto.
     */
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
