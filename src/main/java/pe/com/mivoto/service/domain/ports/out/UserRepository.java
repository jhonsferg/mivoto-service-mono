package pe.com.mivoto.service.domain.ports.out;

import pe.com.mivoto.service.domain.enums.UserRole;
import pe.com.mivoto.service.domain.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Output port for user data access.
 * Manages persistence and retrieval of user accounts.
 */
public interface UserRepository {

    /**
     * Persists or updates a user.
     *
     * @param user The user to save.
     * @return The saved User.
     */
    User save(User user);

    /**
     * Updates an existing user.
     *
     * @param user The user to update.
     * @return The updated User.
     */
    User update(User user);

    /**
     * Finds a user by their ID.
     *
     * @param id The user ID.
     * @return An Optional containing the User if found.
     */
    Optional<User> findById(Long id);

    /**
     * Finds a user by their document number (e.g., DNI, Passport).
     *
     * @param documentNumber The document number.
     * @return An Optional containing the User if found.
     */
    Optional<User> findByDocumentNumber(String documentNumber);

    /**
     * Finds a user by their email address.
     *
     * @param email The email address.
     * @return An Optional containing the User if found.
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their username (if applicable).
     *
     * @param username The username.
     * @return An Optional containing the User if found.
     */
    Optional<User> findByUsername(String username);

    /**
     * Retrieves all users.
     *
     * @return A list of all users.
     */
    List<User> findAll();

    /**
     * Finds users by their role (e.g., VOTER, ADMIN).
     *
     * @param role The user role.
     * @return A list of matching users.
     */
    List<User> findByRole(UserRole role);

    /**
     * Retrieves all active users.
     *
     * @return A list of active users.
     */
    List<User> findActiveUsers();

    /**
     * Deletes a user by their ID.
     *
     * @param id The user ID.
     */
    void deleteById(Long id);

    /**
     * Checks if a user exists with the given document number.
     *
     * @param documentNumber The document number.
     * @return true if exists, false otherwise.
     */
    boolean existsByDocumentNumber(String documentNumber);

    /**
     * Checks if a user exists with the given email.
     *
     * @param email The email address.
     * @return true if exists, false otherwise.
     */
    boolean existsByEmail(String email);

    /**
     * Counts the total number of users.
     *
     * @return The total count.
     */
    Long count();

    /**
     * Counts the number of users with a specific role.
     *
     * @param role The user role.
     * @return The count of users.
     */
    Long countByRole(UserRole role);
}
