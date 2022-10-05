package ml.psychology.api.service.barrett.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ml.psychology.api.domain.barrett.enumeration.SpatialRecognitionAnswerType;

import javax.validation.constraints.NotNull;

@Data
@Schema(name = "SpatialRecognitionTest")
public class SpatialRecognitionTestDTO {
    @NotNull
    @Schema(description = "Id of the test (test index, start with 0)", example = "1")
    int id;

    @Schema(description = "Image URL of the test")
    String imageUrl;

    @NotNull
    @Schema(description = "The user answer")
    private SpatialRecognitionAnswerType userAnswer;
}
