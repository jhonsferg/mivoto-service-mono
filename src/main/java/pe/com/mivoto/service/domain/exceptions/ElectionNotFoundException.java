package pe.com.mivoto.service.domain.exceptions;

public class ElectionNotFoundException extends RuntimeException {

    public ElectionNotFoundException(String message) {
        super(message);
    }

    public ElectionNotFoundException(Long electionId) {
        super("Elección no encontrada con ID: " + electionId);
    }
}
