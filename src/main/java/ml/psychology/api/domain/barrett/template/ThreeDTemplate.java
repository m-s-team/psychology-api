package ml.psychology.api.domain.barrett.template;

import lombok.Data;
import ml.psychology.api.domain.barrett.converter.IntegerArrayToString;
import ml.psychology.api.domain.barrett.converter.TwoDimensionalStringArrayToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
public class ThreeDTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String imageUrl;

    @NotNull
    @Convert(converter = TwoDimensionalStringArrayToString.class)
    private List<List<String>> optionImageUrls;

    @NotNull
    @Convert(converter = IntegerArrayToString.class)
    private List<Integer> correctAnswer;
}
