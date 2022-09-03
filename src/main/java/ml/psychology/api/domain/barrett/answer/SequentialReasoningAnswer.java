package ml.psychology.api.domain.barrett.answer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.converter.IntegerArrayToString;
import ml.psychology.api.domain.barrett.template.SequentialReasoningTemplate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
public class SequentialReasoningAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    private SequentialReasoningTemplate testTemplate;

    @NotNull
    @Convert(converter = IntegerArrayToString.class)
    private List<Integer> userAnswers = List.of(0,0);

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties
    private BarrettTest assessment;
}
