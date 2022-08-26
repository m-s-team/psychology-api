package ml.psychology.api.service.barrett.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(name = "NumericalReasoningQuestion")
public class NumericalReasoningQuestionDTO {

        @NotNull
        @Schema(description = "Question of the test", example = "[[1,2,3,4],[2,-,6,?]]")
        List<List<String>> question;

        @NotNull
        @Schema(description = "The user answer", example = "8")
        int userAnswer;
}
