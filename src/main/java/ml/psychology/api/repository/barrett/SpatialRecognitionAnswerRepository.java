package ml.psychology.api.repository.barrett;

import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.SpatialRecognitionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpatialRecognitionAnswerRepository extends JpaRepository<SpatialRecognitionAnswer, Long> {
    List<SpatialRecognitionAnswer> findByAssessment(BarrettTest assessment);
}
