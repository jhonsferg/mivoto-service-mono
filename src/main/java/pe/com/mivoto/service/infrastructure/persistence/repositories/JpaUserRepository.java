package pe.com.mivoto.service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.mivoto.service.domain.enums.UserRole;
import pe.com.mivoto.service.infrastructure.persistence.entities.UserEntity;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for Users.
 * Provides database access methods for managing UserEntity.
 */
@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Finds a user by document number.
     *
     * @param documentNumber The document number.
     * @return Optional containing the user if found.
     */
    Optional<UserEntity> findByDocumentNumber(String documentNumber);

    /**
     * Finds a user by email address.
     *
     * @param email The email address.
     * @return Optional containing the user if found.
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Finds a user by username (which can be document number or email).
     *
     * @param username The username to search for.
     * @return Optional containing the user if found.
     */
    @Query("SELECT u FROM UserEntity u WHERE u.documentNumber = :username OR u.email = :username")
    Optional<UserEntity> findByUsername(String username);

    /**
     * Finds users by role.
     *
     * @param role The user role.
     * @return List of users.
     */
    List<UserEntity> findByRole(UserRole role);

    /**
     * Finds all active users.
     *
     * @return List of active users.
     */
    List<UserEntity> findByActiveTrue();

    /**
     * Checks if a user with the given document number exists.
     *
     * @param documentNumber The document number.
     * @return true if exists, false otherwise.
     */
    boolean existsByDocumentNumber(String documentNumber);

    /**
     * Checks if a user with the given email exists.
     *
     * @param email The email address.
     * @return true if exists, false otherwise.
     */
    boolean existsByEmail(String email);

    /**
     * Counts users by role.
     *
     * @param role The user role.
     * @return The count of users.
     */
    Long countByRole(UserRole role);
}
