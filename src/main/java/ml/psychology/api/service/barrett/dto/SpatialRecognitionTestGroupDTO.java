package ml.psychology.api.service.barrett.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Schema(name = "SpatialRecognitionTestGroup")
public record SpatialRecognitionTestGroupDTO(

        @NotNull
        @Schema(description = "Main image URL of the test")
        String imageUrl,

        @Schema(description = "List of the tests")
        Set<SpatialRecognitionTestDTO> tests
) {
}
