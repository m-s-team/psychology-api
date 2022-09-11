package ml.psychology.api.repository.barrett;

import ml.psychology.api.domain.barrett.answer.SpatialRecognitionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpatialRecognitionAnswerRepository extends JpaRepository<SpatialRecognitionAnswer, Long> {
}
