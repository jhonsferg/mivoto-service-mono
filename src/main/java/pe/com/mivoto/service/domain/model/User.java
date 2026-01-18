package pe.com.mivoto.service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.mivoto.service.domain.enums.UserRole;

import java.time.LocalDateTime;

/**
 * Represents a user in the system.
 * Users can have different roles such as VOTER, ADMIN, or SUPERVISOR.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Unique identifier for the user.
     */
    private Long id;

    /**
     * National ID or document number (e.g., DNI).
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
     * User's primary email address.
     */
    private String email;

    /**
     * Hashed password for security.
     */
    private String password;

    /**
     * Administrative or operational role assigned to the user.
     */
    private UserRole role;

    /**
     * Indicates if the user account is active and enabled.
     */
    private Boolean active;

    /**
     * Timestamp when the user registered in the system.
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp of the last update to user information.
     */
    private LocalDateTime updatedAt;

    /**
     * Timestamp of the most recent successful login.
     */
    private LocalDateTime lastLogin;

    /**
     * Returns the full name of the user.
     *
     * @return A string combining first and last name.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Checks if the user is authorized to vote.
     * The user must be active and have the VOTER role.
     *
     * @return true if authorized to vote, false otherwise.
     */
    public boolean canVote() {
        return active && role == UserRole.VOTER;
    }

    /**
     * Checks if the user has administrator privileges.
     *
     * @return true if user is an ADMIN.
     */
    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }

    /**
     * Checks if the user has supervisor privileges.
     *
     * @return true if user is a SUPERVISOR.
     */
    public boolean isSupervisor() {
        return role == UserRole.SUPERVISOR;
    }

    /**
     * Updates the last login timestamp to the current time.
     */
    public void updateLastLogin() {
        this.lastLogin = LocalDateTime.now();
    }

    /**
     * Activates the user account.
     */
    public void activate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Deactivates the user account, preventing login and other actions.
     */
    public void deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }
}
