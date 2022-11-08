package ml.psychology.api.domain.barrett.template;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
@Table(name = "THREE_D_TEMPLATE")
public class ThreeDTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String imageUrl;

    @OneToMany(mappedBy = "context", fetch = FetchType.EAGER)
    private Set<ThreeDSubTemplate> subTemplates;
}
