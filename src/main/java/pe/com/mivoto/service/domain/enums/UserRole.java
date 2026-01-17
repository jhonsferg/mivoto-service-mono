package pe.com.mivoto.service.domain.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    VOTER("Votante"),
    ADMIN("Administrador"),
    SUPERVISOR("Supervisor"),
    AUDITOR("Auditor");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }
}
