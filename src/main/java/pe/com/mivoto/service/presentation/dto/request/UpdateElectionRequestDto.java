package pe.com.mivoto.service.presentation.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Request DTO for updating an existing election.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateElectionRequestDto {

    /**
     * The updated title of the election.
     */
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    private String title;

    /**
     * The updated description of the election.
     */
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String description;

    /**
     * The updated start date and time.
     */
    private LocalDateTime startDate;

    /**
     * The updated end date and time.
     */
    private LocalDateTime endDate;
}
