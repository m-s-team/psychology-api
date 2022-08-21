package ml.psychology.api.domain.barrett.subtest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ml.psychology.api.domain.barrett.result.VisualReasoningResult;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Visual Reasoning Subtest
 */
@Data
@Embeddable
public class VisualReasoningSubtest extends Subtest {

    @OneToMany(mappedBy = "assessment")
    @JsonIgnoreProperties
    private Set<VisualReasoningResult> results = new HashSet<>();

}
