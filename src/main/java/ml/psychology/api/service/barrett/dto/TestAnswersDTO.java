package ml.psychology.api.service.barrett.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(name = "TestAnswers")
public class TestAnswersDTO {
        @Schema(
                description = "The answers of the user to the tests",
                example = "[2,5,1,0,3,3,2,1,5,4,0,2,1,2,1,5,4,4,0,2,1,3,4,1,5,2,5,3,5,0]"
        )
        List<@NotNull @Max(value = 5) @Min(0) Integer> userAnswers;
}