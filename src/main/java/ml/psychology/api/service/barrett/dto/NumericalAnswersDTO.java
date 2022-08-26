package ml.psychology.api.service.barrett.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(name = "NumericalAnswers")
public class NumericalAnswersDTO {
        @Schema(
                description = "The answers of the user to the numerical questions",
                example = "[2,5,1,0,3,3,2,1,5,4,0,2,1,2,1,5,4,4,0,2]"
        )
        List<@NotNull Integer> userAnswers;
}