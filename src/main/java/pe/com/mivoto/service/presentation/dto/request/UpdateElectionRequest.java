package pe.com.mivoto.service.presentation.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateElectionRequest {

    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    private String title;

    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String description;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
