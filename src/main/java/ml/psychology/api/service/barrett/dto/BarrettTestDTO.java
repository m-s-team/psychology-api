package ml.psychology.api.service.barrett.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Schema(name = "BarrettTest")
public record BarrettTestDTO(

        @Id
        @Schema(description = "ID of the James Barrett test", example = "1000000001")
        Long id,

        @NotNull
        @Schema(description = "Creation time of this James Barrett test", example = "2022-08-03T02:32:30.100603994Z")
        Instant createdDate,

        @NotNull
        @Schema(description = "Visual reasoning subtest")
        SubtestDTO visualReasoningSubtest,

        @NotNull
        @Schema(description = "numerical reasoning subtest")
        SubtestDTO numericalReasoningSubtest,

        @NotNull
        @Schema(description = "verbal analysis subtest")
        SubtestDTO verbalAnalysisSubtest,

        @NotNull
        @Schema(description = "sequential reasoning subtest")
        SubtestDTO sequentialReasoningSubtest,

        @NotNull
        @Schema(description = "spatial recognition subtest")
        SubtestDTO spatialRecognitionSubtest,

        @NotNull
        @Schema(description = "3D subtest")
        SubtestDTO threeDSubtest
) {
}
