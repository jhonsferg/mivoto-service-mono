package pe.com.mivoto.service.domain.enums;

import lombok.Getter;

/**
 * Enum representing the different roles a user can have within the system.
 */
@Getter
public enum UserRole {
    /**
     * Standard user who can cast votes.
     */
    VOTER("Votante"),
    /**
     * Administrator with full system management permissions.
     */
    ADMIN("Administrador"),
    /**
     * Supervisor with permissions to oversee elections.
     */
    SUPERVISOR("Supervisor"),
    /**
     * Auditor with permissions to view audit logs and verify system integrity.
     */
    AUDITOR("Auditor");

    /**
     * The human-readable name for the role.
     */
    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }
}
