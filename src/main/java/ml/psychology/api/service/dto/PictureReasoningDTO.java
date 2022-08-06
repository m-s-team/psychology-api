package ml.psychology.api.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;

public record PictureReasoningDTO(
        @NotNull
        @Schema(description = "ID of Related WAIS-IV Assessment", example = "10000004567")
        Long waisIvId,

        @NotNull
        @Schema(description = "Creation Time of This Subtest", example = "2022-08-03T02:32:30.100603994Z")
        Instant createdDate,

        @NotNull
        @Schema(description = "Required time for this subtest in minutes", example = "20")
        int requiredTime,

        @Schema(description = "List of the Questions")
        Set<QuestionDTO> questions
) {
}
