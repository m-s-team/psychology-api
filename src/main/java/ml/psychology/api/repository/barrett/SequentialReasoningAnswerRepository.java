package ml.psychology.api.repository.barrett;

import ml.psychology.api.domain.barrett.answer.SequentialReasoningAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequentialReasoningAnswerRepository extends JpaRepository<SequentialReasoningAnswer, Long> {
}
