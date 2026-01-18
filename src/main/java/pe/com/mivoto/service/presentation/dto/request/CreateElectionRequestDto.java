package pe.com.mivoto.service.presentation.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Request DTO for creating a new election.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateElectionRequestDto {

    /**
     * The title of the election.
     */
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    private String title;

    /**
     * Detailed description of the election.
     */
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String description;

    /**
     * The scheduled start date and time of the election.
     */
    @NotNull(message = "La fecha de inicio es obligatoria")
    @Future(message = "La fecha de inicio debe ser futura")
    private LocalDateTime startDate;

    /**
     * The scheduled end date and time of the election.
     */
    @NotNull(message = "La fecha de fin es obligatoria")
    @Future(message = "La fecha de fin debe ser futura")
    private LocalDateTime endDate;

    /**
     * Maximum number of votes allowed per user in this election.
     */
    private Integer maxVotesPerUser;

    /**
     * Whether blank votes are allowed.
     */
    private Boolean allowsBlankVote;

    /**
     * Whether the election requires manual verification of votes.
     */
    private Boolean requiresVerification;
}
