package ml.psychology.api.service.barrett.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Schema(name = "VerbalAnalysisTestGroup")
public record VerbalAnalysisTestGroupDTO(

        @NotNull
        @Schema(description = "Context", example = "علی نسبت به رضا در قسمت بالاتری از یک تپه زندگی می کند، حمید هم در قسمتی بالاتر ازعلی زندگی می کند.")
        String context,

        @Schema(description = "List of the tests")
        Set<VerbalAnalysisTestDTO> tests
) {
}
