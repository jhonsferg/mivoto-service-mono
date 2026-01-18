package pe.com.mivoto.service.presentation.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import pe.com.mivoto.service.domain.exceptions.*;
import pe.com.mivoto.service.presentation.dto.response.ErrorResponseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Global exception handler for the application.
 * Intercepts various exceptions and transforms them into standardized
 * ErrorResponse objects.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

        /**
         * Handles MethodArgumentNotValidException (validation errors).
         *
         * @param ex      The exception.
         * @param request The web request.
         * @return A ResponseEntity with validation error details.
         */
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex,
                        WebRequest request) {
                log.warn("Error de validación: {}", ex.getMessage());
                List<ErrorResponseDto.ValidationError> validationErrors = new ArrayList<>();

                ex.getBindingResult().getAllErrors().forEach(error -> {
                        String fieldName = ((FieldError) error).getField();
                        String errorMessage = error.getDefaultMessage();

                        validationErrors.add(ErrorResponseDto.ValidationError.builder()
                                        .field(fieldName)
                                        .message(errorMessage)
                                        .build());
                });

                ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error("Validation Error")
                                .message("Error de validación en los datos enviados")
                                .path(request.getDescription(false).replace("uri=", ""))
                                .timestamp(LocalDateTime.now())
                                .validationErrors(validationErrors)
                                .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        /**
         * Handles AuthenticationException.
         *
         * @param ex      The exception.
         * @param request The web request.
         * @return A ResponseEntity with UNAUTHORIZED status.
         */
        @ExceptionHandler(AuthenticationException.class)
        public ResponseEntity<ErrorResponseDto> handleAuthenticationException(AuthenticationException ex,
                        WebRequest request) {
                log.error("Error de autenticación: {}", ex.getMessage());
                ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .error("Authentication Error")
                                .message(ex.getMessage())
                                .path(request.getDescription(false).replace("uri=", ""))
                                .timestamp(LocalDateTime.now())
                                .build();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        /**
         * Handles AccessDeniedException.
         *
         * @param ex      The exception.
         * @param request The web request.
         * @return A ResponseEntity with FORBIDDEN status.
         */
        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ErrorResponseDto> handleAccessDeniedException(AccessDeniedException ex,
                        WebRequest request) {
                log.error("Acceso denegado: {}", ex.getMessage());
                ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                                .status(HttpStatus.FORBIDDEN.value())
                                .error("Access Denied")
                                .message("No tiene permisos para acceder a este recurso")
                                .path(request.getDescription(false).replace("uri=", ""))
                                .timestamp(LocalDateTime.now())
                                .build();
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }

        /**
         * Handles UserNotFoundException.
         *
         * @param ex      The exception.
         * @param request The web request.
         * @return A ResponseEntity with NOT_FOUND status.
         */
        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException ex,
                        WebRequest request) {
                log.error("Usuario no encontrado: {}", ex.getMessage());
                ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                                .status(HttpStatus.NOT_FOUND.value())
                                .error("User Not Found")
                                .message(ex.getMessage())
                                .path(request.getDescription(false).replace("uri=", ""))
                                .timestamp(LocalDateTime.now())
                                .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        /**
         * Handles ElectionNotFoundException.
         *
         * @param ex      The exception.
         * @param request The web request.
         * @return A ResponseEntity with NOT_FOUND status.
         */
        @ExceptionHandler(ElectionNotFoundException.class)
        public ResponseEntity<ErrorResponseDto> handleElectionNotFoundException(ElectionNotFoundException ex,
                        WebRequest request) {
                log.error("Elección no encontrada: {}", ex.getMessage());
                ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                                .status(HttpStatus.NOT_FOUND.value())
                                .error("Election Not Found")
                                .message(ex.getMessage())
                                .path(request.getDescription(false).replace("uri=", ""))
                                .timestamp(LocalDateTime.now())
                                .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        /**
         * Handles DuplicateVoteException.
         *
         * @param ex      The exception.
         * @param request The web request.
         * @return A ResponseEntity with CONFLICT status.
         */
        @ExceptionHandler(DuplicateVoteException.class)
        public ResponseEntity<ErrorResponseDto> handleDuplicateVoteException(DuplicateVoteException ex,
                        WebRequest request) {
                log.error("Voto duplicado: {}", ex.getMessage());
                ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                                .status(HttpStatus.CONFLICT.value())
                                .error("Duplicate Vote")
                                .message(ex.getMessage())
                                .path(request.getDescription(false).replace("uri=", ""))
                                .timestamp(LocalDateTime.now())
                                .build();
                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

        /**
         * Handles InvalidElectionException.
         *
         * @param ex      The exception.
         * @param request The web request.
         * @return A ResponseEntity with BAD_REQUEST status.
         */
        @ExceptionHandler(InvalidElectionException.class)
        public ResponseEntity<ErrorResponseDto> handleInvalidElectionException(InvalidElectionException ex,
                        WebRequest request) {
                log.error("Elección inválida: {}", ex.getMessage());
                ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error("Invalid Election")
                                .message(ex.getMessage())
                                .path(request.getDescription(false).replace("uri=", ""))
                                .timestamp(LocalDateTime.now())
                                .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        /**
         * Handles VotingException.
         *
         * @param ex      The exception.
         * @param request The web request.
         * @return A ResponseEntity with BAD_REQUEST status.
         */
        @ExceptionHandler(VotingException.class)
        public ResponseEntity<ErrorResponseDto> handleVotingException(VotingException ex, WebRequest request) {
                log.error("Error de votación: {}", ex.getMessage());
                ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error("Voting Error")
                                .message(ex.getMessage())
                                .path(request.getDescription(false).replace("uri=", ""))
                                .timestamp(LocalDateTime.now())
                                .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        /**
         * Handles IllegalArgumentException.
         *
         * @param ex      The exception.
         * @param request The web request.
         * @return A ResponseEntity with BAD_REQUEST status.
         */
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException ex,
                        WebRequest request) {
                log.error("Argumento ilegal: {}", ex.getMessage());
                ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error("Invalid Argument")
                                .message(ex.getMessage())
                                .path(request.getDescription(false).replace("uri=", ""))
                                .timestamp(LocalDateTime.now())
                                .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        /**
         * Handles IllegalStateException.
         *
         * @param ex      The exception.
         * @param request The web request.
         * @return A ResponseEntity with CONFLICT status.
         */
        @ExceptionHandler(IllegalStateException.class)
        public ResponseEntity<ErrorResponseDto> handleIllegalStateException(IllegalStateException ex,
                        WebRequest request) {
                log.error("Estado ilegal: {}", ex.getMessage());
                ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                                .status(HttpStatus.CONFLICT.value())
                                .error("Invalid State")
                                .message(ex.getMessage())
                                .path(request.getDescription(false).replace("uri=", ""))
                                .timestamp(LocalDateTime.now())
                                .build();
                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

        /**
         * Handles catch-all Exception.
         *
         * @param ex      The exception.
         * @param request The web request.
         * @return A ResponseEntity with INTERNAL_SERVER_ERROR status.
         */
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception ex, WebRequest request) {
                log.error("Error inesperado: ", ex);
                ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .error("Internal Server Error")
                                .message("Ha ocurrido un error interno en el servidor")
                                .path(request.getDescription(false).replace("uri=", ""))
                                .timestamp(LocalDateTime.now())
                                .build();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
}
