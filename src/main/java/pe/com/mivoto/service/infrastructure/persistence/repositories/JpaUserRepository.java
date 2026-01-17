package pe.com.mivoto.service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.mivoto.service.domain.enums.UserRole;
import pe.com.mivoto.service.infrastructure.persistence.entities.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByDocumentNumber(String documentNumber);

    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.documentNumber = :username OR u.email = :username")
    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findByRole(UserRole role);

    List<UserEntity> findByActiveTrue();

    boolean existsByDocumentNumber(String documentNumber);

    boolean existsByEmail(String email);

    Long countByRole(UserRole role);
}
