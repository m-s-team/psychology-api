package ml.psychology.api.repository.barrett;

import ml.psychology.api.domain.barrett.template.NumericalReasoningTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumericalReasoningTemplateRepository extends JpaRepository<NumericalReasoningTemplate, Long> {
}
