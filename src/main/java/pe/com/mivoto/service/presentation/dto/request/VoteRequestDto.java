package pe.com.mivoto.service.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for casting a vote.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteRequestDto {

    /**
     * The ID of the election.
     */
    @NotNull(message = "El ID de la elección es obligatorio")
    private Long electionId;

    /**
     * The ID of the candidate being voted for.
     */
    @NotNull(message = "El ID del candidato es obligatorio")
    private Long candidateId;
}
