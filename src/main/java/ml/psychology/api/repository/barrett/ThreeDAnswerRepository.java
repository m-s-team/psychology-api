package ml.psychology.api.repository.barrett;

import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.ThreeDAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThreeDAnswerRepository extends JpaRepository<ThreeDAnswer, Long> {
    List<ThreeDAnswer> findByAssessment(BarrettTest assessment);
}
