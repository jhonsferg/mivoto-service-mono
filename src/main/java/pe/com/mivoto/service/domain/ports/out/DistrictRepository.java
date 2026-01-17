package pe.com.mivoto.service.domain.ports.out;

import pe.com.mivoto.service.domain.model.District;

import java.util.List;
import java.util.Optional;

public interface DistrictRepository {
    District save(District district);

    District update(District district);

    Optional<District> findById(Long id);

    Optional<District> findByCode(String code);

    List<District> findAll();

    List<District> findByType(String type);

    List<District> findByParentDistrictId(Long parentDistrictId);

    List<District> findRootDistricts();

    List<District> findActiveDistricts();

    List<District> findByNameContaining(String name);

    void deleteById(Long id);

    boolean existsByCode(String code);

    Long count();

    Long countByType(String type);
}
