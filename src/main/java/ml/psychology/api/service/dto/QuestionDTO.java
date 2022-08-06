package ml.psychology.api.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Set;

public record QuestionDTO(
        @Id
        @Schema(description = "ID of the Question", example = "10000099873")
        Long id,

        @NotNull
        @Schema(description = "Image URL of the Question")
        String imageUrl,

        @NotNull
        @Schema(description = "List of the Tests")
        Set<TestDTO> tests
) {
}
