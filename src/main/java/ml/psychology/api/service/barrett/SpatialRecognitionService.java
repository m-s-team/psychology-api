package ml.psychology.api.service.barrett;

import ml.psychology.api.config.Constants;
import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.SpatialRecognitionAnswer;
import ml.psychology.api.domain.barrett.subtest.SpatialRecognitionSubtest;
import ml.psychology.api.domain.barrett.template.SpatialRecognitionTemplate;
import ml.psychology.api.repository.barrett.BarrettTestRepository;
import ml.psychology.api.repository.barrett.SpatialRecognitionAnswerRepository;
import ml.psychology.api.repository.barrett.SpatialRecognitionTemplateRepository;
import ml.psychology.api.service.barrett.dto.SpatialAnswersDTO;
import ml.psychology.api.service.barrett.dto.SpatialRecognitionDTO;
import ml.psychology.api.service.barrett.mapper.SpatialRecognitionMapper;
import org.springframework.stereotype.Service;

import javax.naming.TimeLimitExceededException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
public class SpatialRecognitionService {

    private final BarrettTestRepository barrettTestRepository;
    private final SpatialRecognitionTemplateRepository templateRepository;
    private final SpatialRecognitionAnswerRepository answerRepository;
    private final SpatialRecognitionMapper spatialRecognitionMapper;

    public SpatialRecognitionService(BarrettTestRepository barrettTestRepository, SpatialRecognitionTemplateRepository templateRepository, SpatialRecognitionAnswerRepository answerRepository, SpatialRecognitionMapper spatialRecognitionMapper) {
        this.barrettTestRepository = barrettTestRepository;
        this.templateRepository = templateRepository;
        this.answerRepository = answerRepository;
        this.spatialRecognitionMapper = spatialRecognitionMapper;
    }

    public SpatialRecognitionDTO create(Long assessmentId) throws EntityExistsException {

        BarrettTest assessment = barrettTestRepository.findById(assessmentId).orElseThrow();

        // throw EntityExistsException if subtest already exists
        if (Objects.nonNull(assessment.getSpatialRecognitionSubtest().getCreatedDate()))
            throw new EntityExistsException();

        Instant now = Instant.now();
        SpatialRecognitionSubtest subtest = assessment.getSpatialRecognitionSubtest();
        subtest.setCreatedDate(now);
        subtest.setCompletedDate(now);

        List<SpatialRecognitionTemplate> templates = templateRepository.findAll();
        barrettTestRepository.save(assessment);
        return spatialRecognitionMapper.mergeToDto(
                subtest,
                Constants.SPATIAL_RECOGNITION_REQUIRED_MINUTE,
                templates,
                answerRepository.saveAll(spatialRecognitionMapper.templatesToAnswers(templates, assessment))
        );
    }

    public SpatialRecognitionDTO getById(Long assessmentId) {
        BarrettTest assessment = barrettTestRepository.findById(assessmentId).orElseThrow();

        // throw EntityExistsException if subtest not exists
        if (Objects.isNull(assessment.getSpatialRecognitionSubtest().getCreatedDate()))
            throw new EntityNotFoundException();

        return spatialRecognitionMapper.mergeToDto(
                assessment.getSpatialRecognitionSubtest(),
                Constants.SPATIAL_RECOGNITION_REQUIRED_MINUTE,
                templateRepository.findAll(),
                answerRepository.findByAssessment(assessment)
        );
    }

    public SpatialRecognitionDTO updateUserAnswers(Long assessmentId, SpatialAnswersDTO answers) throws TimeLimitExceededException {
        BarrettTest assessment = barrettTestRepository.findById(assessmentId).orElseThrow();

        // throw EntityExistsException if subtest not exists
        if (Objects.isNull(assessment.getSpatialRecognitionSubtest().getCreatedDate()))
            throw new EntityNotFoundException();

        Instant now = Instant.now();
        Instant createdDate = assessment.getSpatialRecognitionSubtest().getCreatedDate();
        int requiredTime = Constants.SPATIAL_RECOGNITION_REQUIRED_MINUTE;

        if (Duration.between(createdDate, now).toMinutes() > requiredTime)
            throw new TimeLimitExceededException();

        SpatialRecognitionSubtest subtest = assessment.getSpatialRecognitionSubtest();
        subtest.setCompletedDate(now);

        List<SpatialRecognitionAnswer> spatialRecognitionAnswers = answerRepository.findByAssessment(assessment);
        spatialRecognitionMapper.mergeToAnswers(
                answers.getUserAnswers(),
                spatialRecognitionAnswers
        );

        return spatialRecognitionMapper.mergeToDto(
                barrettTestRepository.save(assessment).getSpatialRecognitionSubtest(),
                Constants.VISUAL_REASONING_REQUIRED_MINUTE,
                templateRepository.findAll(),
                answerRepository.saveAll(spatialRecognitionAnswers)
        );
    }}
