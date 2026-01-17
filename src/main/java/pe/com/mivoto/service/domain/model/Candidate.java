package pe.com.mivoto.service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {

    private Long id;
    private Long electionId;
    private Integer number;
    private String name;
    private String party;
    private String description;
    private String photoUrl;
    private Boolean active;
    private Integer voteCount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public boolean canReceiveVotes() {
        return active != null && active;
    }

    public void incrementVoteCount() {
        if (this.voteCount == null) {
            this.voteCount = 0;
        }
        this.voteCount++;
        this.updatedAt = LocalDateTime.now();
    }

    public void activate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    public int getVoteCount() {
        return voteCount != null ? voteCount : 0;
    }
}
