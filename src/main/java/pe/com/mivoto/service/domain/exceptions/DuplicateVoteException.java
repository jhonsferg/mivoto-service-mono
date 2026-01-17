package pe.com.mivoto.service.domain.exceptions;

public class DuplicateVoteException extends VotingException {

    public DuplicateVoteException(String message) {
        super(message);
    }

    public DuplicateVoteException(Long userId, Long electionId) {
        super(String.format("El usuario %d ya ha votado en la elección %d",
                userId, electionId));
    }
}
