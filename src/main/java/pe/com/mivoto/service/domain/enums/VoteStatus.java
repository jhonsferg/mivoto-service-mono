package pe.com.mivoto.service.domain.enums;

import lombok.Getter;

/**
 * Enum representing the status of an individual vote.
 */
@Getter
public enum VoteStatus {
    /**
     * The vote has been submitted but not yet fully processed or confirmed.
     */
    PENDING("Pendiente"),
    /**
     * The vote has been successfully cast and confirmed.
     */
    CONFIRMED("Confirmado"),
    /**
     * The vote was rejected due to an error or invalid data.
     */
    REJECTED("Rechazado"),
    /**
     * The vote is being manually reviewed for potential issues.
     */
    UNDER_REVIEW("En Revisión");

    /**
     * The human-readable name for the status.
     */
    private final String displayName;

    VoteStatus(String displayName) {
        this.displayName = displayName;
    }
}
