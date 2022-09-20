package ml.psychology.api.service.barrett.dto.answer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(name = "NumericalAnswer")
public class NumericalAnswerDTO {

    @NotNull
    int id;

    @NotNull
    int userAnswer;
}
