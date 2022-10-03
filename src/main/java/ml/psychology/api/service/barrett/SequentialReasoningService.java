package ml.psychology.api.service.barrett;

import ml.psychology.api.config.Constants;
import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.SequentialReasoningAnswer;
import ml.psychology.api.domain.barrett.subtest.SequentialReasoningSubtest;
import ml.psychology.api.domain.barrett.template.SequentialReasoningTemplate;
import ml.psychology.api.repository.barrett.BarrettTestRepository;
import ml.psychology.api.repository.barrett.SequentialReasoningAnswerRepository;
import ml.psychology.api.repository.barrett.SequentialReasoningTemplateRepository;
import ml.psychology.api.service.barrett.dto.SequentialReasoningDTO;
import ml.psychology.api.service.barrett.dto.answer.SequentialAnswerDTO;
import ml.psychology.api.service.barrett.mapper.SequentialReasoningMapper;
import org.springframework.stereotype.Service;

import javax.naming.TimeLimitExceededException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.Duration;
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
                Constants.SEQUENTIAL_REASONING_REQUIRED_MINUTE,
                templates,
                answerRepository.saveAll(sequentialReasoningMapper.templatesToAnswers(templates, assessment))
        );
    }

    public SequentialReasoningDTO getById(Long assessmentId) {
        BarrettTest assessment = barrettTestRepository.findById(assessmentId).orElseThrow();

        // throw EntityExistsException if subtest not exists
        if (Objects.isNull(assessment.getSequentialReasoningSubtest().getCreatedDate()))
            throw new EntityNotFoundException();

        return sequentialReasoningMapper.mergeToDto(
                assessment.getSequentialReasoningSubtest(),
                Constants.SEQUENTIAL_REASONING_REQUIRED_MINUTE,
                templateRepository.findAll(),
                answerRepository.findByAssessment(assessment)
        );
    }

    public SequentialReasoningDTO updateUserAnswers(Long assessmentId, List<SequentialAnswerDTO> answers) throws TimeLimitExceededException {
        BarrettTest assessment = barrettTestRepository.findById(assessmentId).orElseThrow();

        // throw EntityExistsException if subtest not exists
        if (Objects.isNull(assessment.getSequentialReasoningSubtest().getCreatedDate()))
            throw new EntityNotFoundException();

        Instant now = Instant.now();
        Instant createdDate = assessment.getSequentialReasoningSubtest().getCreatedDate();
        int requiredTime = Constants.SEQUENTIAL_REASONING_REQUIRED_MINUTE;

        if (Duration.between(createdDate, now).toMinutes() > requiredTime)
            throw new TimeLimitExceededException();

        SequentialReasoningSubtest subtest = assessment.getSequentialReasoningSubtest();
        subtest.setCompletedDate(now);

        List<SequentialReasoningAnswer> sequentialReasoningAnswers = answerRepository.findByAssessment(assessment);
        sequentialReasoningMapper.mergeToAnswers(
                answers,
                sequentialReasoningAnswers
        );

        return sequentialReasoningMapper.mergeToDto(
                barrettTestRepository.save(assessment).getSequentialReasoningSubtest(),
                Constants.VISUAL_REASONING_REQUIRED_MINUTE,
                templateRepository.findAll(),
                answerRepository.saveAll(sequentialReasoningAnswers)
        );
    }
}
