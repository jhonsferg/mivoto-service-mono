package pe.com.mivoto.service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.mivoto.service.domain.enums.ElectionStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an election event within the system.
 * Manages the lifecycle, configuration, and candidates of an election.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Election {

    /**
     * Unique identifier for the election.
     */
    private Long id;

    /**
     * Descriptive title of the election.
     */
    private String title;

    /**
     * Detailed information about the election process.
     */
    private String description;

    /**
     * Current lifecycle status of the election.
     */
    private ElectionStatus status;

    /**
     * Official start date and time of the election.
     */
    private LocalDateTime startDate;

    /**
     * Official end date and time of the election.
     */
    private LocalDateTime endDate;

    /**
     * Maximum number of times a single user can vote in this election.
     */
    private Integer maxVotesPerUser;

    /**
     * Whether users are allowed to cast blank votes.
     */
    private Boolean allowsBlankVote;

    /**
     * Whether signatures/votes require manual verification by an authority.
     */
    private Boolean requiresVerification;

    /**
     * List of candidates participating in this election.
     */
    @Builder.Default
    private List<Candidate> candidates = new ArrayList<>();

    /**
     * Timestamp when the election was created in the system.
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp of the last update to the election details.
     */
    private LocalDateTime updatedAt;

    /**
     * ID of the user who created the election.
     */
    private Long createdBy;

    /**
     * Checks if the election is currently active.
     * An election is active if its status is ACTIVE and the current time is within
     * the start and end dates.
     *
     * @return true if active, false otherwise.
     */
    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return status == ElectionStatus.ACTIVE &&
                now.isAfter(startDate) &&
                now.isBefore(endDate);
    }

    /**
     * Determines if casting votes is allowed for this election.
     * Requires the election to be active and have at least one candidate.
     *
     * @return true if voting is allowed, false otherwise.
     */
    public boolean canVote() {
        return isActive() && !candidates.isEmpty();
    }

    /**
     * Checks if the election is scheduled for the future.
     *
     * @return true if scheduled and start date has not passed.
     */
    public boolean isScheduled() {
        return status == ElectionStatus.SCHEDULED &&
                LocalDateTime.now().isBefore(startDate);
    }

    /**
     * Checks if the election is closed.
     * An election is closed if its status is CLOSED or the end date has passed.
     *
     * @return true if closed, false otherwise.
     */
    public boolean isClosed() {
        return status == ElectionStatus.CLOSED ||
                LocalDateTime.now().isAfter(endDate);
    }

    /**
     * Schedules the election if it is currently in DRAFT status.
     * Updates the status to SCHEDULED and records the update time.
     */
    public void schedule() {
        if (status == ElectionStatus.DRAFT) {
            this.status = ElectionStatus.SCHEDULED;
            this.updatedAt = LocalDateTime.now();
        }
    }

    /**
     * Activates the election if it is currently scheduled.
     * Updates the status to ACTIVE and records the update time.
     */
    public void start() {
        if (status == ElectionStatus.SCHEDULED) {
            this.status = ElectionStatus.ACTIVE;
            this.updatedAt = LocalDateTime.now();
        }
    }

    /**
     * Closes the election if it is currently active.
     * Updates the status to CLOSED and records the update time.
     */
    public void close() {
        if (status == ElectionStatus.ACTIVE) {
            this.status = ElectionStatus.CLOSED;
            this.updatedAt = LocalDateTime.now();
        }
    }

    /**
     * Cancels the election.
     * Updates the status to CANCELLED and records the update time.
     */
    public void cancel() {
        this.status = ElectionStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Adds a candidate to the election.
     * Initializes the candidates list if it is null.
     *
     * @param candidate The candidate to add.
     */
    public void addCandidate(Candidate candidate) {
        if (this.candidates == null) {
            this.candidates = new ArrayList<>();
        }
        this.candidates.add(candidate);
    }

    /**
     * Returns the total number of candidates verified for this election.
     *
     * @return The number of candidates.
     */
    public int getTotalCandidates() {
        return candidates != null ? candidates.size() : 0;
    }

    /**
     * Checks if a specific candidate is part of this election.
     *
     * @param candidateId The ID of the candidate to check.
     * @return true if the candidate exists in this election, false otherwise.
     */
    public boolean hasCandidate(Long candidateId) {
        if (candidates == null) {
            return false;
        }
        return candidates.stream()
                .anyMatch(c -> c.getId().equals(candidateId));
    }
}
