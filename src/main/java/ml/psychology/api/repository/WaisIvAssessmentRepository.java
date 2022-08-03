package ml.psychology.api.repository;

import ml.psychology.api.domain.WaisIvAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaisIvAssessmentRepository extends JpaRepository<WaisIvAssessment, Long> {
    List<WaisIvAssessment> findByUserId(String userId);
}
