package pe.com.mivoto.service.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for user login.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    /**
     * The username (usually document number or email).
     */
    @NotBlank(message = "El usuario es obligatorio")
    private String username;

    /**
     * The user's password.
     */
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
