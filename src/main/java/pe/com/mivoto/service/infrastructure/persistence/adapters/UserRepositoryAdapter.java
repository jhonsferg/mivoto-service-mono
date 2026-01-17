package pe.com.mivoto.service.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.enums.UserRole;
import pe.com.mivoto.service.domain.model.User;
import pe.com.mivoto.service.domain.ports.out.UserRepository;
import pe.com.mivoto.service.infrastructure.persistence.entities.UserEntity;
import pe.com.mivoto.service.infrastructure.persistence.repositories.JpaUserRepository;
import pe.com.mivoto.service.presentation.mappers.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        log.debug("Guardando usuario: {}", user.getEmail());
        UserEntity entity = userMapper.toEntity(user);
        UserEntity saved = jpaUserRepository.save(entity);
        return userMapper.toDomain(saved);
    }

    @Override
    public User update(User user) {
        log.debug("Actualizando usuario: {}", user.getId());
        UserEntity entity = userMapper.toEntity(user);
        UserEntity updated = jpaUserRepository.save(entity);
        return userMapper.toDomain(updated);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByDocumentNumber(String documentNumber) {
        return jpaUserRepository.findByDocumentNumber(documentNumber)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaUserRepository.findByUsername(username)
                .map(userMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll().stream()
                .map(userMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByRole(UserRole role) {
        return jpaUserRepository.findByRole(role).stream()
                .map(userMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findActiveUsers() {
        return jpaUserRepository.findByActiveTrue().stream()
                .map(userMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Eliminando usuario: {}", id);
        jpaUserRepository.deleteById(id);
    }

    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return jpaUserRepository.existsByDocumentNumber(documentNumber);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    public Long count() {
        return jpaUserRepository.count();
    }

    @Override
    public Long countByRole(UserRole role) {
        return jpaUserRepository.countByRole(role);
    }
}
