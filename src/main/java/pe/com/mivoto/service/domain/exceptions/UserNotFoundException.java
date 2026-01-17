package pe.com.mivoto.service.domain.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long userId) {
        super("Usuario no encontrado con ID: " + userId);
    }

    public UserNotFoundException(String field, String value) {
        super(String.format("Usuario no encontrado con %s: %s", field, value));
    }
}
