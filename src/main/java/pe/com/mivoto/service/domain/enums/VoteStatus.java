package pe.com.mivoto.service.domain.enums;

import lombok.Getter;

@Getter
public enum VoteStatus {
    PENDING("Pendiente"),
    CONFIRMED("Confirmado"),
    REJECTED("Rechazado"),
    UNDER_REVIEW("En Revisión");

    private final String displayName;

    VoteStatus(String displayName) {
        this.displayName = displayName;
    }
}
