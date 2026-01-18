package pe.com.mivoto.service.domain.exceptions;

/**
 * Exception thrown when a user cannot be found in the system.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserNotFoundException with the specified detail message.
     *
     * @param message the detail message.
     */
    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new UserNotFoundException with the ID of the missing user.
     *
     * @param userId the ID of the user.
     */
    public UserNotFoundException(Long userId) {
        super("Usuario no encontrado con ID: " + userId);
    }

    /**
     * Constructs a new UserNotFoundException searching by a specific field and
     * value.
     *
     * @param field the field name.
     * @param value the field value.
     */
    public UserNotFoundException(String field, String value) {
        super(String.format("Usuario no encontrado con %s: %s", field, value));
    }
}
