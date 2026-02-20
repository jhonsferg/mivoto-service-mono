package pe.com.mivoto.service.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.District;
import pe.com.mivoto.service.domain.ports.out.DistrictRepository;
import pe.com.mivoto.service.infrastructure.persistence.entities.DistrictEntity;
import pe.com.mivoto.service.infrastructure.persistence.mappers.DistrictEntityMapper;
import pe.com.mivoto.service.infrastructure.persistence.repositories.JpaDistrictRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the District Output Port (DistrictRepository) using the JPA
 * repository.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DistrictRepositoryAdapter implements DistrictRepository {

    private final JpaDistrictRepository jpaDistrictRepository;
    private final DistrictEntityMapper districtEntityMapper;

    /**
     * Saves a new district.
     *
     * @param district The district to save.
     * @return The saved district.
     */
    @Override
    public District save(District district) {
        log.debug("Guardando distrito: {}", district.getName());
        DistrictEntity entity = districtEntityMapper.toEntity(district);
        DistrictEntity saved = jpaDistrictRepository.save(entity);
        return districtEntityMapper.toDomain(saved);
    }

    /**
     * Updates an existing district.
     *
     * @param district The district updates.
     * @return The updated district.
     */
    @Override
    public District update(District district) {
        log.debug("Actualizando distrito: {}", district.getId());
        DistrictEntity entity = districtEntityMapper.toEntity(district);
        DistrictEntity updated = jpaDistrictRepository.save(entity);
        return districtEntityMapper.toDomain(updated);
    }

    /**
     * Finds a district by ID.
     *
     * @param id The district ID.
     * @return Optional containing the district if found.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Optional<District> findById(Long id) {
        return jpaDistrictRepository.findById(id)
                .map(districtEntityMapper::toDomain);
    }

    /**
     * Finds a district by its unique code.
     *
     * @param code The district code.
     * @return Optional containing the district if found.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Optional<District> findByCode(String code) {
        return jpaDistrictRepository.findByCode(code)
                .map(districtEntityMapper::toDomain);
    }

    /**
     * Retrieves all districts.
     *
     * @return List of all districts.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<District> findAll() {
        return jpaDistrictRepository.findAll().stream()
                .map(districtEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds districts by type.
     *
     * @param type The type string.
     * @return List of matching districts.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<District> findByType(String type) {
        return jpaDistrictRepository.findByType(type).stream()
                .map(districtEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds direct sub-districts of a parent district.
     *
     * @param parentDistrictId The parent district ID.
     * @return List of child districts.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<District> findByParentDistrictId(Long parentDistrictId) {
        return jpaDistrictRepository.findByParentDistrictId(parentDistrictId).stream()
                .map(districtEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all root districts (districts with no parent).
     *
     * @return List of root districts.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<District> findRootDistricts() {
        return jpaDistrictRepository.findRootDistricts().stream()
                .map(districtEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all active districts.
     *
     * @return List of active districts.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<District> findActiveDistricts() {
        return jpaDistrictRepository.findByActiveTrue().stream()
                .map(districtEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds districts with names containing the search string.
     *
     * @param name The name substring.
     * @return List of matching districts.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<District> findByNameContaining(String name) {
        return jpaDistrictRepository.findByNameContainingIgnoreCase(name).stream()
                .map(districtEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a district by ID.
     *
     * @param id The district ID.
     */
    @Override
    public void deleteById(Long id) {
        jpaDistrictRepository.deleteById(id);
    }

    /**
     * Checks if a district with the given code exists.
     *
     * @param code The code to check.
     * @return true if exists.
     */
    @Override
    public boolean existsByCode(String code) {
        return jpaDistrictRepository.existsByCode(code);
    }

    /**
     * Counts the total number of districts.
     *
     * @return The count.
     */
    @Override
    public Long count() {
        return jpaDistrictRepository.count();
    }

    /**
     * Counts districts by type.
     *
     * @param type The type string.
     * @return The count.
     */
    @Override
    public Long countByType(String type) {
        return jpaDistrictRepository.countByType(type);
    }
}
