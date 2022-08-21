package ml.psychology.api.service.dto.barrett;

import io.swagger.v3.oas.annotations.media.Schema;
import ml.psychology.api.service.dto.barrett.subtest.VisualReasoningSubtestDTO;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Schema
public record BarrettTestDTO(

        @Id
        @Schema(description = "ID of the James Barrett test", example = "1000000001")
        Long id,

        @NotNull
        @Schema(description = "Creation time of this James Barrett test", example = "2022-08-03T02:32:30.100603994Z")
        Instant createdDate,

        @NotNull
        @Schema(description = "Visual reasoning subtest")
        VisualReasoningSubtestDTO visualReasoningSubtest,

        @NotNull
        @Schema(description = "The owner of this assessment", example = "google-oauth2|120408978933119965034")
        String userId
) {
}
