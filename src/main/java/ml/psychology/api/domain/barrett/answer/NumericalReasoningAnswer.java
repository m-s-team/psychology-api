package ml.psychology.api.domain.barrett.answer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.template.NumericalReasoningTemplate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class NumericalReasoningAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    private NumericalReasoningTemplate testTemplate;

    @NotNull
    private byte userAnswer = -1;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties
    private BarrettTest assessment;
}
