package pe.com.mivoto.service.domain.exceptions;

/**
 * Exception thrown when an election is in an invalid state for a requested
 * operation.
 */
public class InvalidElectionException extends VotingException {

    /**
     * Constructs a new InvalidElectionException with the specified detail message.
     *
     * @param message the detail message.
     */
    public InvalidElectionException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidElectionException with the election ID and reason.
     *
     * @param electionId the ID of the election.
     * @param reason     the reason why the election is invalid.
     */
    public InvalidElectionException(Long electionId, String reason) {
        super(String.format("Elección %d inválida: %s", electionId, reason));
    }
}
