package ml.psychology.api.service.barrett.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ml.psychology.api.domain.barrett.enumeration.VisualReasoningTemplateType;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Schema(name = "VisualReasoningTest")
public class VisualReasoningTestDTO {
        @NotNull
        @Schema(description = "The type of the test", example = "next")
        VisualReasoningTemplateType type;

        @NotNull
        @Schema(description = "Question of the test", example = "تصویر بعدی کدام است؟")
        String question;

        @Schema(description = "Image URL of the test")
        String imageUrl;

        @NotNull
        @Schema(description = "List of the option image URLs")
        Set<String> optionImageUrls;

        @NotNull
        @Schema(description = "The user answer", example = "5")
        int userAnswer;
}
