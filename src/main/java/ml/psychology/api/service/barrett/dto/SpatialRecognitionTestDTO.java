package ml.psychology.api.service.barrett.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ml.psychology.api.domain.barrett.enumeration.SpatialRecognitionAnswerType;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
@Schema(name = "SpatialRecognitionTest")
public class SpatialRecognitionTestDTO {

    @Schema(description = "Image URL of the test")
    String imageUrl;

    @NotNull
    @Schema(description = "List of the option image URLs")
    Set<String> optionImageUrls;

    @NotNull
    @Schema(description = "The user answers")
    private List<SpatialRecognitionAnswerType> userAnswers;
}
