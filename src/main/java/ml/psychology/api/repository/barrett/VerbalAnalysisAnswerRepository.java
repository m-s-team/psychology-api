package ml.psychology.api.repository.barrett;

import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.VerbalAnalysisAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerbalAnalysisAnswerRepository extends JpaRepository<VerbalAnalysisAnswer, Long> {
    List<VerbalAnalysisAnswer> findByAssessment(BarrettTest assessment);
}
