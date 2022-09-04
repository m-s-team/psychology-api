package ml.psychology.api.repository.barrett;

import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.SequentialReasoningAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SequentialReasoningAnswerRepository extends JpaRepository<SequentialReasoningAnswer, Long> {
    List<SequentialReasoningAnswer> findByAssessment(BarrettTest assessment);
}
