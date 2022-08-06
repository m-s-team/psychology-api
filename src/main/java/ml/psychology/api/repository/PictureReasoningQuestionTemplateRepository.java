package ml.psychology.api.repository;

import ml.psychology.api.domain.PictureReasoningQuestionTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureReasoningQuestionTemplateRepository extends JpaRepository<PictureReasoningQuestionTemplate, Long> {
}
