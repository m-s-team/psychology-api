package ml.psychology.api.domain.barrett.answer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.converter.SpatialRecognitionAnswerTypeArrayToString;
import ml.psychology.api.domain.barrett.enumeration.SpatialRecognitionAnswerType;
import ml.psychology.api.domain.barrett.template.SpatialRecognitionTemplate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
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
    @Convert(converter = SpatialRecognitionAnswerTypeArrayToString.class)
    private List<SpatialRecognitionAnswerType> userAnswers = Collections.nCopies(5, SpatialRecognitionAnswerType.NOT_ANSWERED);

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties
    private BarrettTest assessment;
}
