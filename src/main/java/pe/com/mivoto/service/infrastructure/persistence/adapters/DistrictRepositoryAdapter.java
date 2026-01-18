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

    @Override
    public District save(District district) {
        log.debug("Guardando distrito: {}", district.getName());
        DistrictEntity entity = districtEntityMapper.toEntity(district);
        DistrictEntity saved = jpaDistrictRepository.save(entity);
        return districtEntityMapper.toDomain(saved);
    }

    @Override
    public District update(District district) {
        log.debug("Actualizando distrito: {}", district.getId());
        DistrictEntity entity = districtEntityMapper.toEntity(district);
        DistrictEntity updated = jpaDistrictRepository.save(entity);
        return districtEntityMapper.toDomain(updated);
    }

    @Override
    public Optional<District> findById(Long id) {
        return jpaDistrictRepository.findById(id)
                .map(districtEntityMapper::toDomain);
    }

    @Override
    public Optional<District> findByCode(String code) {
        return jpaDistrictRepository.findByCode(code)
                .map(districtEntityMapper::toDomain);
    }

    @Override
    public List<District> findAll() {
        return jpaDistrictRepository.findAll().stream()
                .map(districtEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<District> findByType(String type) {
        return jpaDistrictRepository.findByType(type).stream()
                .map(districtEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<District> findByParentDistrictId(Long parentDistrictId) {
        return jpaDistrictRepository.findByParentDistrictId(parentDistrictId).stream()
                .map(districtEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<District> findRootDistricts() {
        return jpaDistrictRepository.findRootDistricts().stream()
                .map(districtEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<District> findActiveDistricts() {
        return jpaDistrictRepository.findByActiveTrue().stream()
                .map(districtEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<District> findByNameContaining(String name) {
        return jpaDistrictRepository.findByNameContainingIgnoreCase(name).stream()
                .map(districtEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaDistrictRepository.deleteById(id);
    }

    @Override
    public boolean existsByCode(String code) {
        return jpaDistrictRepository.existsByCode(code);
    }

    @Override
    public Long count() {
        return jpaDistrictRepository.count();
    }

    @Override
    public Long countByType(String type) {
        return jpaDistrictRepository.countByType(type);
    }
}
