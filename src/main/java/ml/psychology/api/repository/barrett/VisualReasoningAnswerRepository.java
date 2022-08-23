package ml.psychology.api.repository.barrett;

import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.VisualReasoningAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisualReasoningAnswerRepository extends JpaRepository<VisualReasoningAnswer, Long> {
    List<VisualReasoningAnswer> findByAssessment(BarrettTest assessment);
}
