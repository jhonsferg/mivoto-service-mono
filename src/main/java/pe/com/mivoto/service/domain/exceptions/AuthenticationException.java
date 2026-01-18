package pe.com.mivoto.service.domain.exceptions;

/**
 * Exception thrown when an authentication error occurs.
 */
public class AuthenticationException extends RuntimeException {

    /**
     * Constructs a new AuthenticationException with the specified detail message.
     *
     * @param message the detail message.
     */
    public AuthenticationException(String message) {
        super(message);
    }

    /**
     * Constructs a new AuthenticationException with the specified detail message
     * and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
