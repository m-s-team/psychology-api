package ml.psychology.api.service.barrett;

import ml.psychology.api.config.Constants;
import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.NumericalReasoningAnswer;
import ml.psychology.api.domain.barrett.subtest.NumericalReasoningSubtest;
import ml.psychology.api.domain.barrett.template.NumericalReasoningTemplate;
import ml.psychology.api.repository.barrett.BarrettTestRepository;
import ml.psychology.api.repository.barrett.NumericalReasoningAnswerRepository;
import ml.psychology.api.repository.barrett.NumericalReasoningTemplateRepository;
import ml.psychology.api.service.barrett.dto.NumericalAnswersDTO;
import ml.psychology.api.service.barrett.dto.NumericalReasoningDTO;
import ml.psychology.api.service.barrett.mapper.BarrettTestMapper;
import ml.psychology.api.service.barrett.mapper.NumericalReasoningMapper;
import org.springframework.stereotype.Service;

import javax.naming.TimeLimitExceededException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
public class NumericalReasoningService {
    private BarrettTestRepository barrettTestRepository;
    private NumericalReasoningTemplateRepository templateRepository;
    private BarrettTestMapper barrettTestMapper;
    private NumericalReasoningMapper numericalReasoningMapper;
    private NumericalReasoningAnswerRepository answerRepository;

    public NumericalReasoningService(BarrettTestRepository barrettTestRepository, NumericalReasoningTemplateRepository templateRepository, BarrettTestMapper barrettTestMapper, NumericalReasoningMapper numericalReasoningMapper, NumericalReasoningAnswerRepository answerRepository) {
        this.barrettTestRepository = barrettTestRepository;
        this.templateRepository = templateRepository;
        this.barrettTestMapper = barrettTestMapper;
        this.numericalReasoningMapper = numericalReasoningMapper;
        this.answerRepository = answerRepository;
    }

    public NumericalReasoningDTO create(Long assessmentId) {

        BarrettTest assessment = barrettTestRepository.findById(assessmentId).orElseThrow();

        // throw EntityExistsException if subtest already exists
        if (Objects.nonNull(assessment.getNumericalReasoningSubtest().getCreatedDate()))
            throw new EntityExistsException();

        Instant now = Instant.now();
        NumericalReasoningSubtest subtest = assessment.getNumericalReasoningSubtest();
        subtest.setCreatedDate(now);
        subtest.setCompletedDate(now);

        List<NumericalReasoningTemplate> templates = templateRepository.findAll();
        barrettTestRepository.save(assessment);

        return numericalReasoningMapper.mergeToDto(
                subtest,
                Constants.NUMERICAL_REASONING_REQUIRED_MINUTE,
                templateRepository.findAll(),
                answerRepository.saveAll(numericalReasoningMapper.templatesToAnswers(templates, assessment))
        );
    }

    public NumericalReasoningDTO getById(Long assessmentId) {
        BarrettTest assessment = barrettTestRepository.findById(assessmentId).orElseThrow();

        // throw EntityExistsException if subtest not exists
        if (Objects.isNull(assessment.getNumericalReasoningSubtest().getCreatedDate()))
            throw new EntityNotFoundException();

        return numericalReasoningMapper.mergeToDto(
                assessment.getNumericalReasoningSubtest(),
                Constants.NUMERICAL_REASONING_REQUIRED_MINUTE,
                templateRepository.findAll(),
                answerRepository.findByAssessment(assessment)
        );
    }

    public NumericalReasoningDTO updateUserAnswers(Long assessmentId, NumericalAnswersDTO answers) throws TimeLimitExceededException {
        BarrettTest assessment = barrettTestRepository.findById(assessmentId).orElseThrow();

        // throw EntityExistsException if subtest not exists
        if (Objects.isNull(assessment.getNumericalReasoningSubtest().getCreatedDate()))
            throw new EntityNotFoundException();

        Instant now = Instant.now();
        Instant createdDate = assessment.getNumericalReasoningSubtest().getCreatedDate();
        int requiredTime = Constants.NUMERICAL_REASONING_REQUIRED_MINUTE;

        if (Duration.between(createdDate, now).toMinutes() > requiredTime)
            throw new TimeLimitExceededException();

        NumericalReasoningSubtest subtest = assessment.getNumericalReasoningSubtest();
        subtest.setCompletedDate(now);

        List<NumericalReasoningAnswer> numericalReasoningAnswers = answerRepository.findByAssessment(assessment);
        numericalReasoningMapper.mergeToAnswers(
                answers.getUserAnswers(),
                numericalReasoningAnswers
        );

        return numericalReasoningMapper.mergeToDto(
                barrettTestRepository.save(assessment).getNumericalReasoningSubtest(),
                Constants.NUMERICAL_REASONING_REQUIRED_MINUTE,
                templateRepository.findAll(),
                answerRepository.saveAll(numericalReasoningAnswers)
        );
    }
}
