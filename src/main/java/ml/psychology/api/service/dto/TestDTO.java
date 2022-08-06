package ml.psychology.api.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Set;

public record TestDTO(
        @Id
        @Schema(description = "ID of the Question", example = "107590055231")
        Long id,

        @NotNull
        @Schema(description = "List of the Options")
        Set<OptionDTO> options,

        @NotNull
        @Schema(description = "The User Answer")
        int userAnswer
) {
}
