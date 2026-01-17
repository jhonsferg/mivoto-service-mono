package pe.com.mivoto.service.domain.ports.out;

import pe.com.mivoto.service.domain.enums.UserRole;
import pe.com.mivoto.service.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    User update(User user);

    Optional<User> findById(Long id);

    Optional<User> findByDocumentNumber(String documentNumber);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    List<User> findByRole(UserRole role);

    List<User> findActiveUsers();

    void deleteById(Long id);

    boolean existsByDocumentNumber(String documentNumber);

    boolean existsByEmail(String email);

    Long count();

    Long countByRole(UserRole role);
}
