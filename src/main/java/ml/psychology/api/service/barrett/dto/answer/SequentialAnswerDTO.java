package ml.psychology.api.service.barrett.dto.answer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(name = "SequentialAnswer")
public class SequentialAnswerDTO {

    @NotNull
    int id;

    @NotNull
    @Schema(description = "The user answers", example = "[5, 7]")
    List<@Max(18) @Min(0) Integer> userAnswers;
}
