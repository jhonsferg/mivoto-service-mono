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
public class District {

    private Long id;
    private String name;
    private String code;
    private String type;
    private Long parentDistrictId;
    private Integer registeredVoters;
    private Boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public boolean isNational() {
        return "NATIONAL".equalsIgnoreCase(type) || parentDistrictId == null;
    }

    public boolean isRegional() {
        return "REGIONAL".equalsIgnoreCase(type);
    }

    public boolean isLocal() {
        return "LOCAL".equalsIgnoreCase(type);
    }

    public boolean hasParent() {
        return parentDistrictId != null;
    }

    public void activate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateVoterCount(Integer count) {
        this.registeredVoters = count;
        this.updatedAt = LocalDateTime.now();
    }
}
