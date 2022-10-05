package ml.psychology.api.domain.barrett.answer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.enumeration.SpatialRecognitionAnswerType;
import ml.psychology.api.domain.barrett.template.SpatialRecognitionSubTemplate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class SpatialRecognitionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    private SpatialRecognitionSubTemplate testSubTemplate;

    @NotNull
    private SpatialRecognitionAnswerType userAnswer = SpatialRecognitionAnswerType.NOT_ANSWERED;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties
    private BarrettTest assessment;
}
