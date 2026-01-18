package pe.com.mivoto.service.domain.exceptions;

/**
 * Exception thrown when an election cannot be found in the system.
 */
public class ElectionNotFoundException extends RuntimeException {

    /**
     * Constructs a new ElectionNotFoundException with the specified detail message.
     *
     * @param message the detail message.
     */
    public ElectionNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new ElectionNotFoundException with the ID of the missing
     * election.
     *
     * @param electionId the ID of the election.
     */
    public ElectionNotFoundException(Long electionId) {
        super("Elección no encontrada con ID: " + electionId);
    }
}
