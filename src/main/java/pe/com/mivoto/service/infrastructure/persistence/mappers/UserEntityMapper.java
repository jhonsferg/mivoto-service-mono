package pe.com.mivoto.service.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.User;
import pe.com.mivoto.service.infrastructure.persistence.entities.UserEntity;

/**
 * Mapper for User entity.
 * Converts between User domain model and UserEntity.
 */
@Component
public class UserEntityMapper {

    /**
     * Converts UserEntity to User domain model.
     *
     * @param entity The UserEntity.
     * @return The User domain model.
     */
    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return User.builder()
                .id(entity.getId())
                .documentNumber(entity.getDocumentNumber())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .role(entity.getRole())
                .active(entity.getActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .lastLogin(entity.getLastLogin())
                .build();
    }

    /**
     * Converts User domain model to UserEntity.
     *
     * @param domain The User domain model.
     * @return The UserEntity.
     */
    public UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }

        return UserEntity.builder()
                .id(domain.getId())
                .documentNumber(domain.getDocumentNumber())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .role(domain.getRole())
                .active(domain.getActive())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .lastLogin(domain.getLastLogin())
                .build();
    }
}
