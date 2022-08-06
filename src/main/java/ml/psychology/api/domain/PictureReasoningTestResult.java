package ml.psychology.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class PictureReasoningTestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    private PictureReasoningTestTemplate test;

    @NotNull
    @Min(0)
    @Max(5)
    private int userAnswer = 0;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties
    private WaisIvAssessment assessment;
}
