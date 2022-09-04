package ml.psychology.api.service.barrett.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;

@Schema(name = "SequentialReasoning")
public record SequentialReasoningDTO(
        @Schema(description = "Creation time of this sequential reasoning subtest", example = "2022-08-03T02:32:30.100603994Z")
        Instant createdDate,
        @Schema(description = "Completed time of this sequential reasoning subtest", example = "2022-08-03T02:45:12.10865477Z")
        Instant completedDate,
        @NotNull
        @Schema(description = "Required time for this subtest in minutes", example = "15")
        int requiredTime,
        @Schema(description = "List of the Questions")
        Set<SequentialReasoningTestDTO> tests
) {
}
