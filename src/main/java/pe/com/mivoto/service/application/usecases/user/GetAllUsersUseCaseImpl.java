package pe.com.mivoto.service.application.usecases.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.User;
import pe.com.mivoto.service.domain.ports.out.UserRepository;

import java.util.List;

/**
 * Use case implementation for retrieving all users.
 */
@Component
@RequiredArgsConstructor
public class GetAllUsersUseCaseImpl {

    private final UserRepository userRepository;

    /**
     * Retrieves all users from the repository.
     *
     * @return List of all users.
     */
    public List<User> execute() {
        return userRepository.findAll();
    }
}
