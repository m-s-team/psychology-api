package ml.psychology.api.domain.barrett.template;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ml.psychology.api.domain.barrett.converter.StringArrayToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
public class ThreeDSubTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ThreeDTemplate context;

    @NotNull
    @Convert(converter = StringArrayToString.class)
    private List<String> optionImageUrls;

    @NotNull
    @Min(1)
    @Max(5)
    private byte correctAnswer;
}
