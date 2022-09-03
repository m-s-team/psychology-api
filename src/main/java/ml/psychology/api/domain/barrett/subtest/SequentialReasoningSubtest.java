package ml.psychology.api.domain.barrett.subtest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ml.psychology.api.domain.barrett.answer.SequentialReasoningAnswer;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Sequential Reasoning Subtest
 */
@Data
@Embeddable
public class SequentialReasoningSubtest extends Subtest {

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<SequentialReasoningAnswer> answers = new HashSet<>();
}
