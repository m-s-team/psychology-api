package ml.psychology.api.service.barrett;

import ml.psychology.api.config.Constants;
import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.VerbalAnalysisAnswer;
import ml.psychology.api.domain.barrett.subtest.VerbalAnalysisSubtest;
import ml.psychology.api.domain.barrett.template.VerbalAnalysisSubTemplate;
import ml.psychology.api.repository.barrett.BarrettTestRepository;
import ml.psychology.api.repository.barrett.VerbalAnalysisAnswerRepository;
import ml.psychology.api.repository.barrett.VerbalAnalysisSubTemplateRepository;
import ml.psychology.api.repository.barrett.VerbalAnalysisTemplateRepository;
import ml.psychology.api.service.barrett.dto.VerbalAnalysisDTO;
import ml.psychology.api.service.barrett.dto.answer.VerbalAnswerDTO;
import ml.psychology.api.service.barrett.mapper.VerbalAnalysisMapper;
import org.springframework.stereotype.Service;

import javax.naming.TimeLimitExceededException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
public class VerbalAnalysisService {
    private final BarrettTestRepository barrettTestRepository;
    private final VerbalAnalysisTemplateRepository templateRepository;
    private final VerbalAnalysisMapper verbalAnalysisMapper;
    private final VerbalAnalysisAnswerRepository answerRepository;
    private final VerbalAnalysisSubTemplateRepository subTemplateRepository;

    public VerbalAnalysisService(BarrettTestRepository barrettTestRepository, VerbalAnalysisTemplateRepository templateRepository, VerbalAnalysisMapper verbalAnalysisMapper, VerbalAnalysisAnswerRepository answerRepository, VerbalAnalysisSubTemplateRepository subTemplateRepository) {
        this.barrettTestRepository = barrettTestRepository;
        this.templateRepository = templateRepository;
        this.verbalAnalysisMapper = verbalAnalysisMapper;
        this.answerRepository = answerRepository;
        this.subTemplateRepository = subTemplateRepository;
    }

    public VerbalAnalysisDTO create(Long assessmentId) throws EntityExistsException {

        BarrettTest assessment = barrettTestRepository.findById(assessmentId).orElseThrow();

        // throw EntityExistsException if subtest already exists
        if (Objects.nonNull(assessment.getVerbalAnalysisSubtest().getCreatedDate()))
            throw new EntityExistsException();

        Instant now = Instant.now();
        VerbalAnalysisSubtest subtest = assessment.getVerbalAnalysisSubtest();
        subtest.setCreatedDate(now);
        subtest.setCompletedDate(now);

        List<VerbalAnalysisSubTemplate> subTemplates = subTemplateRepository.findAll();
        barrettTestRepository.save(assessment);

        return verbalAnalysisMapper.mergeToDto(
                subtest,
                Constants.VERBAL_ANALYSIS_REQUIRED_MINUTE,
                templateRepository.findAll(),
                answerRepository.saveAll(verbalAnalysisMapper.subTemplatesToAnswers(subTemplates, assessment))
        );
    }

    public VerbalAnalysisDTO getById(Long assessmentId) {
        BarrettTest assessment = barrettTestRepository.findById(assessmentId).orElseThrow();

        // throw EntityExistsException if subtest not exists
        if (Objects.isNull(assessment.getVerbalAnalysisSubtest().getCreatedDate()))
            throw new EntityNotFoundException();

        return verbalAnalysisMapper.mergeToDto(
                assessment.getVerbalAnalysisSubtest(),
                Constants.VERBAL_ANALYSIS_REQUIRED_MINUTE,
                templateRepository.findAll(),
                answerRepository.findByAssessment(assessment)
        );
    }

    public VerbalAnalysisDTO updateUserAnswers(Long assessmentId, List<VerbalAnswerDTO> answers) throws TimeLimitExceededException {
        BarrettTest assessment = barrettTestRepository.findById(assessmentId).orElseThrow();

        // throw EntityExistsException if subtest not exists
        if (Objects.isNull(assessment.getVerbalAnalysisSubtest().getCreatedDate()))
            throw new EntityNotFoundException();

        Instant now = Instant.now();
        Instant createdDate = assessment.getVerbalAnalysisSubtest().getCreatedDate();
        int requiredTime = Constants.VERBAL_ANALYSIS_REQUIRED_MINUTE;

        if (Duration.between(createdDate, now).toMinutes() > requiredTime)
            throw new TimeLimitExceededException();

        VerbalAnalysisSubtest subtest = assessment.getVerbalAnalysisSubtest();
        subtest.setCompletedDate(now);

        List<VerbalAnalysisAnswer> verbalAnalysisAnswers = answerRepository.findByAssessment(assessment);
        verbalAnalysisMapper.mergeToAnswers(
                answers,
                verbalAnalysisAnswers
        );

        return verbalAnalysisMapper.mergeToDto(
                barrettTestRepository.save(assessment).getVerbalAnalysisSubtest(),
                Constants.VISUAL_REASONING_REQUIRED_MINUTE,
                templateRepository.findAll(),
                answerRepository.saveAll(verbalAnalysisAnswers)
        );
    }
}
