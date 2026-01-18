package pe.com.mivoto.service.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.mivoto.service.domain.enums.UserRole;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for User details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    /**
     * Unique identifier of the user.
     */
    private Long id;

    /**
     * User's document number (e.g., DNI).
     */
    private String documentNumber;

    /**
     * User's first name.
     */
    private String firstName;

    /**
     * User's last name.
     */
    private String lastName;

    /**
     * User's email address.
     */
    private String email;

    /**
     * User's role (e.g., ADMIN, VOTER).
     */
    private UserRole role;

    /**
     * Whether the user account is active.
     */
    private Boolean active;

    /**
     * Timestamp of the last successful login.
     */
    private LocalDateTime lastLogin;
}
