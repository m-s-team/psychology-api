package ml.psychology.api.domain.barrett.template;

import lombok.Data;
import ml.psychology.api.domain.converter.StringArrayToString;
import ml.psychology.api.domain.enumeration.VisualReasoningTemplateType;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
public class VisualReasoningTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private VisualReasoningTemplateType type;
    @NotNull
    private String question;
    @NotNull
    private String imageUrl;

    @NotNull
    @Convert(converter = StringArrayToString.class)
    private List<String> optionImageUrls;

    @NotNull
    @Min(1)
    @Max(5)
    private byte correctAnswer;
}
