package ml.psychology.api.repository.barrett;

import ml.psychology.api.domain.barrett.template.VerbalAnalysisTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerbalAnalysisTemplateRepository extends JpaRepository<VerbalAnalysisTemplate, Long> {
}
