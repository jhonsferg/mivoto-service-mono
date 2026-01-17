package pe.com.mivoto.service.domain.enums;

import lombok.Getter;

@Getter
public enum ElectionStatus {
    DRAFT("Borrador"),
    SCHEDULED("Programada"),
    ACTIVE("Activa"),
    CLOSED("Cerrada"),
    CANCELLED("Cancelada");

    private final String displayName;

    ElectionStatus(String displayName) {
        this.displayName = displayName;
    }
}
