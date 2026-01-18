package pe.com.mivoto.service.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating a new candidate.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCandidateRequestDto {

    /**
     * The ID of the election the candidate belongs to.
     */
    @NotNull(message = "El ID de la elección es obligatorio")
    private Long electionId;

    /**
     * The candidate's number on the ballot.
     */
    @NotNull(message = "El número del candidato es obligatorio")
    @Positive(message = "El número debe ser positivo")
    private Integer number;

    /**
     * The full name of the candidate.
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String name;

    /**
     * The political party the candidate represents.
     */
    @Size(max = 150, message = "El partido no puede exceder 150 caracteres")
    private String party;

    /**
     * A brief description or biography of the candidate.
     */
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String description;

    /**
     * URL to the candidate's photo.
     */
    @Size(max = 500, message = "La URL de la foto no puede exceder 500 caracteres")
    private String photoUrl;
}
