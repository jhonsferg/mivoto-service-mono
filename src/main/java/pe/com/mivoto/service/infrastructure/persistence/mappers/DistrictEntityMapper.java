package pe.com.mivoto.service.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.District;
import pe.com.mivoto.service.infrastructure.persistence.entities.DistrictEntity;

/**
 * Mapper for District entity.
 * Converts between District domain model and DistrictEntity.
 */
@Component
public class DistrictEntityMapper {

    /**
     * Converts DistrictEntity to District domain model.
     *
     * @param entity The DistrictEntity.
     * @return The District domain model.
     */
    public District toDomain(DistrictEntity entity) {
        if (entity == null) {
            return null;
        }

        return District.builder()
                .id(entity.getId())
                .name(entity.getName())
                .code(entity.getCode())
                .type(entity.getType())
                .parentDistrictId(entity.getParentDistrictId())
                .registeredVoters(entity.getRegisteredVoters())
                .active(entity.getActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    /**
     * Converts District domain model to DistrictEntity.
     *
     * @param domain The District domain model.
     * @return The DistrictEntity.
     */
    public DistrictEntity toEntity(District domain) {
        if (domain == null) {
            return null;
        }

        return DistrictEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .code(domain.getCode())
                .type(domain.getType())
                .parentDistrictId(domain.getParentDistrictId())
                .registeredVoters(domain.getRegisteredVoters())
                .active(domain.getActive())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
