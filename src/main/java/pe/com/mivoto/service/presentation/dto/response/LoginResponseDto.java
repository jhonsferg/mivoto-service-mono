package pe.com.mivoto.service.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for successful login responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    /**
     * JWT access token.
     */
    private String accessToken;

    /**
     * Refresh token.
     */
    private String refreshToken;

    /**
     * Type of token (usually "Bearer").
     */
    @Builder.Default
    private String tokenType = "Bearer";

    /**
     * Expiration timestamp of the access token.
     */
    private LocalDateTime expiresAt;

    /**
     * Basic user information.
     */
    private UserDto user;
}
