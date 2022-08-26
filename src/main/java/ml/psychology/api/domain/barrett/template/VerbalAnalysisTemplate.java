package ml.psychology.api.domain.barrett.template;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
public class VerbalAnalysisTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String context;

    @OneToMany(mappedBy = "context")
    private Set<VerbalAnalysisSubTemplate> questions;
}
