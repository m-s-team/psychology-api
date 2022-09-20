package ml.psychology.api.service.barrett.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Schema(name = "VerbalAnalysisTest")
public class VerbalAnalysisTestDTO {
        @NotNull
        @Schema(description = "Id of the test (test index, start with 0)", example = "1")
        int id;

        @NotNull
        @Schema(description = "Question of the test", example = "چه کسی بالاتر از همه زندگی می کند؟")
        String question;

        @NotNull
        @Schema(description = "List of the options", example = "[علی, رضا, حمید]")
        Set<String> options;

        @NotNull
        @Schema(description = "The user answer", example = "2")
        int userAnswer;
}
