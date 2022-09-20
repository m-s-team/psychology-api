package ml.psychology.api.service.barrett.dto.answer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Schema(name = "VerbalAnswer")
public class VerbalAnswerDTO {

    @NotNull
    int id;

    @NotNull
    @Max(6)
    @Min(0)
    int userAnswer;
}
