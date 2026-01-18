package pe.com.mivoto.service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents a geographical or administrative district where elections take
 * place.
 * Districts can have a hierarchical structure (e.g., National -> Regional ->
 * Local).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class District {

    /**
     * Unique identifier for the district.
     */
    private Long id;

    /**
     * Name of the district.
     */
    private String name;

    /**
     * Institutional or geographical code for the district.
     */
    private String code;

    /**
     * Level of the district (e.g., "NATIONAL", "REGIONAL", "LOCAL").
     */
    private String type;

    /**
     * ID of the parent district in the hierarchy.
     */
    private Long parentDistrictId;

    /**
     * Total number of registered voters in this district.
     */
    private Integer registeredVoters;

    /**
     * Indicates if the district is currently active for electoral processes.
     */
    private Boolean active;

    /**
     * Timestamp when the district record was created.
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp when the district record was last updated.
     */
    private LocalDateTime updatedAt;

    /**
     * Checks if the district is at a national level.
     *
     * @return true if national or has no parent, false otherwise.
     */
    public boolean isNational() {
        return "NATIONAL".equalsIgnoreCase(type) || parentDistrictId == null;
    }

    /**
     * Checks if the district is at a regional level.
     *
     * @return true if regional.
     */
    public boolean isRegional() {
        return "REGIONAL".equalsIgnoreCase(type);
    }

    /**
     * Checks if the district is at a local level.
     *
     * @return true if local.
     */
    public boolean isLocal() {
        return "LOCAL".equalsIgnoreCase(type);
    }

    /**
     * Checks if this district belongs to a parent district.
     *
     * @return true if it has a parent district.
     */
    public boolean hasParent() {
        return parentDistrictId != null;
    }

    /**
     * Activates the district.
     */
    public void activate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Deactivates the district.
     */
    public void deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Updates the count of registered voters and records the update time.
     *
     * @param count The new voter count.
     */
    public void updateVoterCount(Integer count) {
        this.registeredVoters = count;
        this.updatedAt = LocalDateTime.now();
    }
}
