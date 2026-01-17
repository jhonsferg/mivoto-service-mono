package pe.com.mivoto.service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.mivoto.service.domain.enums.UserRole;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String documentNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean canVote() {
        return active && role == UserRole.VOTER;
    }

    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }

    public boolean isSupervisor() {
        return role == UserRole.SUPERVISOR;
    }

    public void updateLastLogin() {
        this.lastLogin = LocalDateTime.now();
    }

    public void activate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }
}
