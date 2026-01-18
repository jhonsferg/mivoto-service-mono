package pe.com.mivoto.service.domain.exceptions;

/**
 * Exception thrown when a user attempts to vote more than once in the same
 * election.
 */
public class DuplicateVoteException extends VotingException {

    /**
     * Constructs a new DuplicateVoteException with the specified detail message.
     *
     * @param message the detail message.
     */
    public DuplicateVoteException(String message) {
        super(message);
    }

    /**
     * Constructs a new DuplicateVoteException with IDs of the user and election
     * involved.
     *
     * @param userId     the ID of the user.
     * @param electionId the ID of the election.
     */
    public DuplicateVoteException(Long userId, Long electionId) {
        super(String.format("El usuario %d ya ha votado en la elección %d",
                userId, electionId));
    }
}
