package pe.com.mivoto.service.domain.exceptions;

public class InvalidElectionException extends VotingException {

    public InvalidElectionException(String message) {
        super(message);
    }

    public InvalidElectionException(Long electionId, String reason) {
        super(String.format("Elección %d inválida: %s", electionId, reason));
    }
}
