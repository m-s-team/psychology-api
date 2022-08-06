package ml.psychology.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class PictureReasoningQuestionTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imageUrl;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
    @JsonIgnoreProperties
    private Set<PictureReasoningTestTemplate> tests = new HashSet<>();
}
