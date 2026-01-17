package pe.com.mivoto.service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.mivoto.service.domain.enums.ElectionStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Election {

    private Long id;
    private String title;
    private String description;
    private ElectionStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer maxVotesPerUser;
    private Boolean allowsBlankVote;
    private Boolean requiresVerification;

    @Builder.Default
    private List<Candidate> candidates = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;

    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return status == ElectionStatus.ACTIVE &&
                now.isAfter(startDate) &&
                now.isBefore(endDate);
    }

    public boolean canVote() {
        return isActive() && !candidates.isEmpty();
    }

    public boolean isScheduled() {
        return status == ElectionStatus.SCHEDULED &&
                LocalDateTime.now().isBefore(startDate);
    }

    public boolean isClosed() {
        return status == ElectionStatus.CLOSED ||
                LocalDateTime.now().isAfter(endDate);
    }

    public void start() {
        if (status == ElectionStatus.SCHEDULED) {
            this.status = ElectionStatus.ACTIVE;
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void close() {
        if (status == ElectionStatus.ACTIVE) {
            this.status = ElectionStatus.CLOSED;
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void cancel() {
        this.status = ElectionStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    public void addCandidate(Candidate candidate) {
        if (this.candidates == null) {
            this.candidates = new ArrayList<>();
        }
        this.candidates.add(candidate);
    }

    public int getTotalCandidates() {
        return candidates != null ? candidates.size() : 0;
    }

    public boolean hasCandidate(Long candidateId) {
        if (candidates == null) {
            return false;
        }
        return candidates.stream()
                .anyMatch(c -> c.getId().equals(candidateId));
    }
}
