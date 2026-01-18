package pe.com.mivoto.service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.mivoto.service.infrastructure.persistence.entities.DistrictEntity;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for Districts.
 * Provides database access methods for managing DistrictEntity.
 */
@Repository
public interface JpaDistrictRepository extends JpaRepository<DistrictEntity, Long> {

    /**
     * Finds a district by its unique code.
     *
     * @param code The district code.
     * @return Optional containing the district if found.
     */
    Optional<DistrictEntity> findByCode(String code);

    /**
     * Finds districts by type.
     *
     * @param type The district type.
     * @return List of districts.
     */
    List<DistrictEntity> findByType(String type);

    /**
     * Finds districts by their parent district ID (e.g., municipalities in a
     * state).
     *
     * @param parentDistrictId The parent district ID.
     * @return List of child districts.
     */
    List<DistrictEntity> findByParentDistrictId(Long parentDistrictId);

    /**
     * Finds root districts (districts with no parent).
     *
     * @return List of root districts.
     */
    @Query("SELECT d FROM DistrictEntity d WHERE d.parentDistrictId IS NULL")
    List<DistrictEntity> findRootDistricts();

    /**
     * Finds all active districts.
     *
     * @return List of active districts.
     */
    List<DistrictEntity> findByActiveTrue();

    /**
     * Finds districts by name containing search text (case insensitive).
     *
     * @param name The name to search for.
     * @return List of districts.
     */
    List<DistrictEntity> findByNameContainingIgnoreCase(String name);

    /**
     * Checks if a district with the given code exists.
     *
     * @param code The district code.
     * @return true if exists, false otherwise.
     */
    boolean existsByCode(String code);

    /**
     * Counts districts by type.
     *
     * @param type The district type.
     * @return The count of districts.
     */
    Long countByType(String type);
}
