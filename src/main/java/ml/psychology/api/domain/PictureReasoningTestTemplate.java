package ml.psychology.api.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class PictureReasoningTestTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private String imageUrl4;
    private String imageUrl5;

    @NotNull
    @Min(1)
    @Max(5)
    private int correctAnswer;

    @ManyToOne
    private PictureReasoningQuestionTemplate question;
}
