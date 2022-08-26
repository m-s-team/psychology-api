package ml.psychology.api.domain.barrett.answer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.template.VerbalAnalysisSubTemplate;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class VerbalAnalysisAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    private VerbalAnalysisSubTemplate testSubTemplate;

    @NotNull
    @Min(0)
    @Max(6)
    private byte userAnswer = 0;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties
    private BarrettTest assessment;
}
