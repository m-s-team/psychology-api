package ml.psychology.api.repository.barrett;

import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.NumericalReasoningAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NumericalReasoningAnswerRepository extends JpaRepository <NumericalReasoningAnswer, Long> {
    List<NumericalReasoningAnswer> findByAssessment(BarrettTest assessment);
}
