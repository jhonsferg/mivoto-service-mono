package pe.com.mivoto.service.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for changing user password.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDto {

    /**
     * The current password of the user.
     */
    @NotBlank(message = "La contraseña actual es obligatoria")
    private String oldPassword;

    /**
     * The new password to be set.
     */
    @NotBlank(message = "La nueva contraseña es obligatoria")
    @Size(min = 6, max = 50, message = "La contraseña debe tener entre 6 y 50 caracteres")
    private String newPassword;
}
