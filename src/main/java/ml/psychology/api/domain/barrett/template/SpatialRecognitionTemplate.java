package ml.psychology.api.domain.barrett.template;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
public class SpatialRecognitionTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String imageUrl;

    @OneToMany(mappedBy = "context", fetch = FetchType.EAGER)
    private Set<SpatialRecognitionSubTemplate> subTemplates;
}
