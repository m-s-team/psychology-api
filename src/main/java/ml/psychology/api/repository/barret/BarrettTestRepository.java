package ml.psychology.api.repository.barret;

import ml.psychology.api.domain.barrett.BarrettTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarrettTestRepository extends JpaRepository<BarrettTest, Long> {
    List<BarrettTest> findByUserId(String userId);
}
