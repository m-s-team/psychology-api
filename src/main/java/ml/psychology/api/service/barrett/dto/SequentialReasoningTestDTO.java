package ml.psychology.api.service.barrett.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
@Schema(name = "SequentialReasoningTest")
public class SequentialReasoningTestDTO {

    @NotNull
    @Schema(description = "Id of the test (test index, start with 0)", example = "1")
    int id;

    @Schema(description = "Image URL of the test")
    String imageUrl;

    @NotNull
    @Schema(description = "List of the option image URLs")
    Set<String> optionImageUrls;

    @NotNull
    @Schema(description = "The user answers", example = "[5, 7]")
    List<Integer> userAnswers;
}
