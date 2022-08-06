package ml.psychology.api.repository;

import ml.psychology.api.domain.PictureReasoningTestTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureReasoningTestTemplateRepository extends JpaRepository<PictureReasoningTestTemplate, Long> {
}
