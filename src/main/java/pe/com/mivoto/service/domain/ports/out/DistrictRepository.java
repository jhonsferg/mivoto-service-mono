package pe.com.mivoto.service.domain.ports.out;

import pe.com.mivoto.service.domain.model.District;

import java.util.List;
import java.util.Optional;

/**
 * Output port for district data access.
 * Manages persistence and retrieval of geographical district information.
 */
public interface DistrictRepository {

    /**
     * Persists a district entity.
     *
     * @param district The district to save.
     * @return The saved District.
     */
    District save(District district);

    /**
     * Updates an existing district.
     *
     * @param district The district to update.
     * @return The updated District.
     */
    District update(District district);

    /**
     * Finds a district by its ID.
     *
     * @param id The district ID.
     * @return An Optional containing the District if found.
     */
    Optional<District> findById(Long id);

    /**
     * Finds a district by its unique code.
     *
     * @param code The district code.
     * @return An Optional containing the District if found.
     */
    Optional<District> findByCode(String code);

    /**
     * Retrieves all districts.
     *
     * @return A list of all districts.
     */
    List<District> findAll();

    /**
     * Finds districts by their type (e.g., REGION, PROVINCE).
     *
     * @param type The district type.
     * @return A list of matching districts.
     */
    List<District> findByType(String type);

    /**
     * Finds child districts belonging to a parent district.
     *
     * @param parentDistrictId The ID of the parent district.
     * @return A list of child districts.
     */
    List<District> findByParentDistrictId(Long parentDistrictId);

    /**
     * Finds all root-level districts (districts with no parent).
     *
     * @return A list of root districts.
     */
    List<District> findRootDistricts();

    /**
     * Retrieves all active districts.
     *
     * @return A list of active districts.
     */
    List<District> findActiveDistricts();

    /**
     * Finds districts whose names contain the given string.
     *
     * @param name The name substring to search for.
     * @return A list of matching districts.
     */
    List<District> findByNameContaining(String name);

    /**
     * Deletes a district by its ID.
     *
     * @param id The ID of the district to delete.
     */
    void deleteById(Long id);

    /**
     * Checks if a district with the given code exists.
     *
     * @param code The district code.
     * @return true if exists, false otherwise.
     */
    boolean existsByCode(String code);

    /**
     * Counts the total number of districts.
     *
     * @return The total count.
     */
    Long count();

    /**
     * Counts the number of districts of a specific type.
     *
     * @param type The district type.
     * @return The count of districts.
     */
    Long countByType(String type);
}
