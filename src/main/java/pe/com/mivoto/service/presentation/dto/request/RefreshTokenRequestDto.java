package pe.com.mivoto.service.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for refreshing an authentication token.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequestDto {

    /**
     * The refresh token provided during login.
     */
    @NotBlank(message = "El refresh token es obligatorio")
    private String refreshToken;
}
