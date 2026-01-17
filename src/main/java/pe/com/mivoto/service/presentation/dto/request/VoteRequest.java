package pe.com.mivoto.service.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteRequest {

    @NotNull(message = "El ID de la elección es obligatorio")
    private Long electionId;

    @NotNull(message = "El ID del candidato es obligatorio")
    private Long candidateId;
}
