package ml.psychology.api.service;

import ml.psychology.api.config.Constants;
import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.VisualReasoningAnswer;
import ml.psychology.api.domain.barrett.subtest.VisualReasoningSubtest;
import ml.psychology.api.domain.barrett.template.VisualReasoningTemplate;
import ml.psychology.api.repository.barrett.BarrettTestRepository;
import ml.psychology.api.repository.barrett.VisualReasoningAnswerRepository;
import ml.psychology.api.repository.barrett.VisualReasoningTemplateRepository;
import ml.psychology.api.service.dto.barrett.TestAnswersDTO;
import ml.psychology.api.service.dto.barrett.VisualReasoningDTO;
import ml.psychology.api.service.mapper.BarrettTestMapper;
import org.springframework.stereotype.Service;

import javax.naming.TimeLimitExceededException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
public class VisualReasoningService {

    private final BarrettTestRepository barrettTestRepository;
    private final VisualReasoningTemplateRepository templateRepository;
    private final VisualReasoningAnswerRepository answerRepository;
    private final BarrettTestMapper barrettTestMapper;

    public VisualReasoningService(BarrettTestRepository barrettTestRepository,
                                  VisualReasoningTemplateRepository templateRepository,
                                  VisualReasoningAnswerRepository answerRepository,
                                  BarrettTestMapper barrettTestMapper) {
        this.barrettTestRepository = barrettTestRepository;
        this.templateRepository = templateRepository;
        this.answerRepository = answerRepository;
        this.barrettTestMapper = barrettTestMapper;
    }

    public VisualReasoningDTO create(Long assessmentId) throws EntityExistsException {

        BarrettTest assessment = barrettTestRepository.findById(assessmentId).orElseThrow();

        // throw EntityExistsException if subtest already exists
        if (Objects.nonNull(assessment.getVisualReasoningSubtest().getCreatedDate()))
            throw new EntityExistsException();

        Instant createdDate = Instant.now();
        VisualReasoningSubtest subtest = assessment.getVisualReasoningSubtest();
        subtest.setCreatedDate(createdDate);
        subtest.setCompletedDate(createdDate);

        List<VisualReasoningTemplate> templates = templateRepository.findAll();
        barrettTestRepository.save(assessment);

        return barrettTestMapper.mergeToVisualReasoningDto(
                subtest,
                Constants.VISUAL_REASONING_REQUIRED_MINUTE,
                templateRepository.findAll(),
                answerRepository.saveAll(barrettTestMapper.templatesToAnswers(templates, assessment))
        );
    }

    public VisualReasoningDTO getById(Long assessmentId) {
        BarrettTest assessment = barrettTestRepository.findById(assessmentId).orElseThrow();

        // throw EntityExistsException if subtest not exists
        if (Objects.isNull(assessment.getVisualReasoningSubtest().getCreatedDate()))
            throw new EntityNotFoundException();

        return barrettTestMapper.mergeToVisualReasoningDto(
                assessment.getVisualReasoningSubtest(),
                Constants.VISUAL_REASONING_REQUIRED_MINUTE,
                templateRepository.findAll(),
                answerRepository.findByAssessment(assessment)
        );
    }

    public VisualReasoningDTO updateUserAnswers(Long assessmentId, TestAnswersDTO answers) throws TimeLimitExceededException {
        BarrettTest assessment = barrettTestRepository.findById(assessmentId).orElseThrow();

        // throw EntityExistsException if subtest not exists
        if (Objects.isNull(assessment.getVisualReasoningSubtest().getCreatedDate()))
            throw new EntityNotFoundException();

        Instant now = Instant.now();
        Instant createdDate = assessment.getVisualReasoningSubtest().getCreatedDate();
        int requiredTime = Constants.VISUAL_REASONING_REQUIRED_MINUTE;

        if (Duration.between(createdDate, now).toMinutes() > requiredTime)
            throw new TimeLimitExceededException();

        VisualReasoningSubtest subtest = assessment.getVisualReasoningSubtest();
        subtest.setCompletedDate(now);

        List<VisualReasoningAnswer> visualReasoningAnswers = answerRepository.findByAssessment(assessment);
        barrettTestMapper.mergeToVisualReasoningAnswers(
                answers.getUserAnswers(),
                visualReasoningAnswers
        );

        return barrettTestMapper.mergeToVisualReasoningDto(
                barrettTestRepository.save(assessment).getVisualReasoningSubtest(),
                Constants.VISUAL_REASONING_REQUIRED_MINUTE,
                templateRepository.findAll(),
                answerRepository.saveAll(visualReasoningAnswers)
        );
    }

}
