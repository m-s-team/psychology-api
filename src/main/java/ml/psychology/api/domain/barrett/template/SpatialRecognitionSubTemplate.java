package ml.psychology.api.domain.barrett.template;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ml.psychology.api.domain.barrett.enumeration.SpatialRecognitionAnswerType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class SpatialRecognitionSubTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private SpatialRecognitionTemplate context;

    @NotNull
    private String imageUrl;

    @NotNull
    private SpatialRecognitionAnswerType correctAnswer;

}
