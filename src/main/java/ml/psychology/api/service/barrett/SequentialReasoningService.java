package ml.psychology.api.service.barrett;

import ml.psychology.api.config.Constants;
import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.subtest.SequentialReasoningSubtest;
import ml.psychology.api.domain.barrett.template.SequentialReasoningTemplate;
import ml.psychology.api.repository.barrett.BarrettTestRepository;
import ml.psychology.api.repository.barrett.SequentialReasoningAnswerRepository;
import ml.psychology.api.repository.barrett.SequentialReasoningTemplateRepository;
import ml.psychology.api.service.barrett.dto.SequentialReasoningDTO;
import ml.psychology.api.service.barrett.mapper.SequentialReasoningMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
public class SequentialReasoningService {
    private final BarrettTestRepository barrettTestRepository;
    private final SequentialReasoningTemplateRepository templateRepository;
    private final SequentialReasoningMapper sequentialReasoningMapper;
    private final SequentialReasoningAnswerRepository answerRepository;

    public SequentialReasoningService(BarrettTestRepository barrettTestRepository, SequentialReasoningTemplateRepository templateRepository, SequentialReasoningMapper sequentialReasoningMapper, SequentialReasoningAnswerRepository answerRepository) {
        this.barrettTestRepository = barrettTestRepository;
        this.templateRepository = templateRepository;
        this.sequentialReasoningMapper = sequentialReasoningMapper;
        this.answerRepository = answerRepository;
    }


    public SequentialReasoningDTO create(Long assessmentId) throws EntityExistsException {

        BarrettTest assessment = barrettTestRepository.findById(assessmentId).orElseThrow();

        // throw EntityExistsException if subtest already exists
        if (Objects.nonNull(assessment.getSequentialReasoningSubtest().getCreatedDate()))
            throw new EntityExistsException();

        Instant now = Instant.now();
        SequentialReasoningSubtest subtest = assessment.getSequentialReasoningSubtest();
        subtest.setCreatedDate(now);
        subtest.setCompletedDate(now);

        List<SequentialReasoningTemplate> templates = templateRepository.findAll();
        barrettTestRepository.save(assessment);

        return sequentialReasoningMapper.mergeToDto(
                subtest,
                Constants.VERBAL_ANALYSIS_REQUIRED_MINUTE,
                templateRepository.findAll(),
                answerRepository.saveAll(sequentialReasoningMapper.templatesToAnswers(templates, assessment))
        );
    }
}
