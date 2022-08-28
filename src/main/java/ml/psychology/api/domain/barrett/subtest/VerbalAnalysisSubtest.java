package ml.psychology.api.domain.barrett.subtest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ml.psychology.api.domain.barrett.answer.VerbalAnalysisAnswer;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Verbal Analysis Subtest
 */
@Data
@Embeddable
public class VerbalAnalysisSubtest extends Subtest {
    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<VerbalAnalysisAnswer> answers = new HashSet<>();
}
