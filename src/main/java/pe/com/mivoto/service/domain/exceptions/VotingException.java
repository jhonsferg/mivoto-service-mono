package pe.com.mivoto.service.domain.exceptions;

/**
 * General exception for voting-related errors.
 */
public class VotingException extends RuntimeException {

    /**
     * Constructs a new VotingException with the specified detail message.
     *
     * @param message the detail message.
     */
    public VotingException(String message) {
        super(message);
    }

    /**
     * Constructs a new VotingException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public VotingException(String message, Throwable cause) {
        super(message, cause);
    }
}
