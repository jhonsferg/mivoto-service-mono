package pe.com.mivoto.service.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.enums.UserRole;
import pe.com.mivoto.service.domain.model.User;
import pe.com.mivoto.service.domain.ports.out.UserRepository;
import pe.com.mivoto.service.infrastructure.persistence.entities.UserEntity;
import pe.com.mivoto.service.infrastructure.persistence.mappers.UserEntityMapper;
import pe.com.mivoto.service.infrastructure.persistence.repositories.JpaUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Persistence adapter for Users.
 * Implements the User Output Port (UserRepository) using the JPA repository.
 * Handles data conversion between Domain Models and JPA Entities.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final UserEntityMapper userEntityMapper;

    /**
     * Saves a user to the database.
     *
     * @param user The domain user model.
     * @return The saved user model.
     */
    @Override
    public User save(User user) {
        log.debug("Guardando usuario: {}", user.getEmail());
        UserEntity entity = userEntityMapper.toEntity(user);
        UserEntity saved = jpaUserRepository.save(entity);
        return userEntityMapper.toDomain(saved);
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user The domain user model.
     * @return The updated user model.
     */
    @Override
    public User update(User user) {
        log.debug("Actualizando usuario: {}", user.getId());
        UserEntity entity = userEntityMapper.toEntity(user);
        UserEntity updated = jpaUserRepository.save(entity);
        return userEntityMapper.toDomain(updated);
    }

    /**
     * Finds a user by ID.
     *
     * @param id The user ID.
     * @return Optional containing the user if found.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id)
                .map(userEntityMapper::toDomain);
    }

    /**
     * Finds a user by document number.
     *
     * @param documentNumber The document number.
     * @return Optional containing the user if found.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Optional<User> findByDocumentNumber(String documentNumber) {
        return jpaUserRepository.findByDocumentNumber(documentNumber)
                .map(userEntityMapper::toDomain);
    }

    /**
     * Finds a user by email address.
     *
     * @param email The email address.
     * @return Optional containing the user if found.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(userEntityMapper::toDomain);
    }

    /**
     * Finds a user by username (document number or email).
     *
     * @param username The username.
     * @return Optional containing the user if found.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return jpaUserRepository.findByUsername(username)
                .map(userEntityMapper::toDomain);
    }

    /**
     * Retrieves all users.
     *
     * @return List of all users.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<User> findAll() {
        return jpaUserRepository.findAll().stream()
                .map(userEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Finds users by role.
     *
     * @param role The user role.
     * @return List of users.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<User> findByRole(UserRole role) {
        return jpaUserRepository.findByRole(role).stream()
                .map(userEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all active users.
     *
     * @return List of active users.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<User> findActiveUsers() {
        return jpaUserRepository.findByActiveTrue().stream()
                .map(userEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a user by ID.
     *
     * @param id The user ID.
     */
    @Override
    public void deleteById(Long id) {
        log.debug("Eliminando usuario: {}", id);
        jpaUserRepository.deleteById(id);
    }

    /**
     * Checks if a user with the given document number exists.
     *
     * @param documentNumber The document number.
     * @return true if exists, false otherwise.
     */
    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return jpaUserRepository.existsByDocumentNumber(documentNumber);
    }

    /**
     * Checks if a user with the given email exists.
     *
     * @param email The email address.
     * @return true if exists, false otherwise.
     */
    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    /**
     * Counts all users.
     *
     * @return The total count of users.
     */
    @Override
    public Long count() {
        return jpaUserRepository.count();
    }

    /**
     * Counts users by role.
     *
     * @param role The user role.
     * @return The count of users.
     */
    @Override
    public Long countByRole(UserRole role) {
        return jpaUserRepository.countByRole(role);
    }
}
