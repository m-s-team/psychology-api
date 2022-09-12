package ml.psychology.api.service.barrett.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ml.psychology.api.domain.barrett.enumeration.SpatialRecognitionAnswerType;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(name = "SpatialAnswers")
public class SpatialAnswersDTO {
        @Schema(
                description = "The answers of the user to the spatial questions",
                example = """
                        [
                                ["NOT_ANSWERED","YES","NO","NO","YES"],
                                ["NO","NO","NO","NO","YES"],
                                ["YES","YES","YES","NO","NOT_ANSWERED"],
                                ["NO","NO","NO","NO","YES"],
                                ["YES","NOT_ANSWERED","YES","NO","NOT_ANSWERED"],
                                ["NOT_ANSWERED","YES","YES","NO","YES"],
                                ["NOT_ANSWERED","NOT_ANSWERED","YES","NO","NOT_ANSWERED"],
                                ["YES","YES","NOT_ANSWERED","NO","YES"],
                                ["YES","NOT_ANSWERED","YES","NO","NOT_ANSWERED"],
                                ["NOT_ANSWERED","NO","YES","NO","NO"],
                                ["YES","YES","YES","NOT_ANSWERED","NOT_ANSWERED"],
                                ["NOT_ANSWERED","YES","YES","YES","NO"],
                                ["YES","YES","YES","YES","NOT_ANSWERED"],
                                ["YES","NOT_ANSWERED","NOT_ANSWERED","YES","NOT_ANSWERED"]
                        ]"""
        )
        List<List<@NotNull SpatialRecognitionAnswerType>> userAnswers;
}