package ml.psychology.api.domain.barrett.template;

import lombok.Data;
import ml.psychology.api.domain.barrett.converter.IntegerArrayToString;
import ml.psychology.api.domain.barrett.converter.StringArrayToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
public class SequentialReasoningTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String imageUrl;

    @NotNull
    @Convert(converter = StringArrayToString.class)
    private List<String> optionImageUrls;

    @NotNull
    @Convert(converter = IntegerArrayToString.class)
    private List<Integer> correctAnswers;
}
