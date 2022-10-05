package ml.psychology.api.service.barrett.dto.answer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ml.psychology.api.domain.barrett.enumeration.SpatialRecognitionAnswerType;

import javax.validation.constraints.NotNull;

@Data
@Schema(name = "SpatialAnswer")
public class SpatialAnswerDTO {

    @NotNull
    int id;

    @NotNull
    SpatialRecognitionAnswerType userAnswer;

}
