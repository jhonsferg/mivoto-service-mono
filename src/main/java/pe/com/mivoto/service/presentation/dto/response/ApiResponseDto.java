package pe.com.mivoto.service.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Generalized API response wrapper.
 * Provides a consistent structure for all API responses, including success
 * status, message, data, and timestamp.
 *
 * @param <T> The type of the data being returned.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDto<T> {

    /**
     * Indicates if the request was successful.
     */
    private Boolean success;

    /**
     * A descriptive message about the response.
     */
    private String message;

    /**
     * The actual data returned by the API.
     */
    private T data;

    /**
     * The timestamp when the response was generated.
     */
    private LocalDateTime timestamp;

    /**
     * Creates a success response with data.
     */
    public static <T> ApiResponseDto<T> success(T data) {
        return ApiResponseDto.<T>builder()
                .success(true)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponseDto<T> success(String message, T data) {
        return ApiResponseDto.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponseDto<T> error(String message) {
        return ApiResponseDto.<T>builder()
                .success(false)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
