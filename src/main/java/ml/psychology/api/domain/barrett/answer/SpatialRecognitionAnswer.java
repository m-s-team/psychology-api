package ml.psychology.api.domain.barrett.answer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.converter.IntegerArrayToString;
import ml.psychology.api.domain.barrett.enumeration.SpatialRecognitionAnswerType;
import ml.psychology.api.domain.barrett.template.SpatialRecognitionTemplate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
public class SpatialRecognitionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    private SpatialRecognitionTemplate testTemplate;

    @NotNull
    @Convert(converter = IntegerArrayToString.class)
    private List<SpatialRecognitionAnswerType> userAnswers;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties
    private BarrettTest assessment;
}
