package ml.psychology.api.service.barrett.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(name = "SequentialReasoningSubtest")
public record SequentialReasoningSubtestDTO(
        @Schema(description = "Creation time of this sequential reasoning subtest", example = "2022-08-03T02:32:30.100603994Z")
        Instant createdDate,
        @Schema(description = "Completed time of this sequential reasoning subtest", example = "2022-08-03T02:45:12.10865477Z")
        Instant completedDate
) {
}
