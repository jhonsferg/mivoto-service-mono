package pe.com.mivoto.service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.mivoto.service.infrastructure.persistence.entities.DistrictEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaDistrictRepository extends JpaRepository<DistrictEntity, Long> {
    Optional<DistrictEntity> findByCode(String code);

    List<DistrictEntity> findByType(String type);

    List<DistrictEntity> findByParentDistrictId(Long parentDistrictId);

    @Query("SELECT d FROM DistrictEntity d WHERE d.parentDistrictId IS NULL")
    List<DistrictEntity> findRootDistricts();

    List<DistrictEntity> findByActiveTrue();

    List<DistrictEntity> findByNameContainingIgnoreCase(String name);

    boolean existsByCode(String code);

    Long countByType(String type);
}
