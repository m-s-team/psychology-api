package ml.psychology.api.domain.barrett.template;

import lombok.Data;
import ml.psychology.api.config.Constants;
import ml.psychology.api.domain.barrett.converter.NumericalReasoningQuestionToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Entity
public class NumericalReasoningTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Convert(converter = NumericalReasoningQuestionToString.class)
    @Pattern(regexp = Constants.NUMERICAL_REASONING_QUESTION)
    private List<List<String>> question;

    @NotNull
    private byte correctAnswer;
}
