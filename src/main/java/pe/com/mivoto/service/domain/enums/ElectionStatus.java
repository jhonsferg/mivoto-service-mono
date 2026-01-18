package pe.com.mivoto.service.domain.enums;

import lombok.Getter;

/**
 * Enum representing the lifecycle status of an election.
 */
@Getter
public enum ElectionStatus {
    /**
     * The election is being created and is not yet visible to the general public.
     */
    DRAFT("Borrador"),
    /**
     * The election has been finalized and scheduled for a future date.
     */
    SCHEDULED("Programada"),
    /**
     * The election is currently open and accepting votes.
     */
    ACTIVE("Activa"),
    /**
     * The election period has ended and no more votes are accepted.
     */
    CLOSED("Cerrada"),
    /**
     * The election was cancelled and is no longer valid.
     */
    CANCELLED("Cancelada");

    /**
     * The human-readable name for the status.
     */
    private final String displayName;

    ElectionStatus(String displayName) {
        this.displayName = displayName;
    }
}
