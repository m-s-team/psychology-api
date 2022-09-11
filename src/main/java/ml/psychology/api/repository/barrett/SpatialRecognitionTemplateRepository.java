package ml.psychology.api.repository.barrett;

import ml.psychology.api.domain.barrett.template.SpatialRecognitionTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpatialRecognitionTemplateRepository extends JpaRepository<SpatialRecognitionTemplate, Long> {
}
