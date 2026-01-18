package pe.com.mivoto.service.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for error responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {

    /**
     * HTTP status code.
     */
    private Integer status;

    /**
     * Error category or short description.
     */
    private String error;

    /**
     * Detailed error message.
     */
    private String message;

    /**
     * Request path where the error occurred.
     */
    private String path;

    /**
     * Timestamp of the error.
     */
    private LocalDateTime timestamp;

    /**
     * List of field-specific validation errors.
     */
    private List<ValidationError> validationErrors;

    /**
     * Details of a single validation error.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ValidationError {

        /**
         * The field that failed validation.
         */
        private String field;

        /**
         * The validation error message.
         */
        private String message;
    }
}
