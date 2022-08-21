package ml.psychology.api.domain.barrett.template;

import lombok.Data;
import ml.psychology.api.domain.barrett.enumeration.VisualReasoningTemplateType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
    private String optionImageUrl1;
    @NotNull
    private String optionImageUrl2;
    @NotNull
    private String optionImageUrl3;
    @NotNull
    private String optionImageUrl4;
    private String optionImageUrl5;

    @NotNull
    @Min(1)
    @Max(5)
    private int correctAnswer;
}
