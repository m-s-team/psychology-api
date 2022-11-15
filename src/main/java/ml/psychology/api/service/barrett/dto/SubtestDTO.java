package ml.psychology.api.service.barrett.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Instant;

@Data
@Schema(name = "Subtest")
public class SubtestDTO {
        @Schema(description = "Creation time of this subtest", example = "2022-08-03T02:32:30.100603994Z")
        Instant createdDate;
        @Schema(description = "Required times for this subtest in minutes", example = "8")
        int requiredTime;
        @Schema(description = "Completed time of this subtest", example = "2022-08-03T02:45:12.10865477Z")
        Instant completedDate;
}