package ml.psychology.api.service;

import ml.psychology.api.config.Constants;
import ml.psychology.api.domain.PictureReasoningQuestionTemplate;
import ml.psychology.api.domain.enumeration.WaisIvSubTestStatus;
import ml.psychology.api.repository.PictureReasoningQuestionTemplateRepository;
import ml.psychology.api.repository.WaisIvAssessmentRepository;
import ml.psychology.api.service.dto.OptionDTO;
import ml.psychology.api.service.dto.PictureReasoningDTO;
import ml.psychology.api.service.dto.QuestionDTO;
import ml.psychology.api.service.dto.TestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class WaisIvPictureReasoningService {

    private final WaisIvAssessmentRepository waisIvAssessmentRepository;
    private final PictureReasoningQuestionTemplateRepository questionRepository;

    @Value("${api.static.baseUrl}")
    private String baseUrl;

    public WaisIvPictureReasoningService(WaisIvAssessmentRepository waisIvAssessmentRepository,
                                         PictureReasoningQuestionTemplateRepository questionRepository) {
        this.waisIvAssessmentRepository = waisIvAssessmentRepository;
        this.questionRepository = questionRepository;
    }

    public PictureReasoningDTO create(Long assessmentId) throws EntityExistsException {

        // throw EntityExistsException if subtest already exists
        waisIvAssessmentRepository.findById(assessmentId).ifPresent(assessment -> {
            if (assessment.getPictureReasoningStatus() != WaisIvSubTestStatus.None)
                throw new EntityExistsException();
        });

        PictureReasoningDTO result = new PictureReasoningDTO(assessmentId, Instant.now(), Constants.PICTURE_REASONING_REQUIRED_MINUTE, new LinkedHashSet<>());
        // get all questions from database
        List<PictureReasoningQuestionTemplate> questions = questionRepository.findAll();

        questions.stream().forEach(question -> {
            QuestionDTO questionDTO = new QuestionDTO(question.getId(), baseUrl + question.getImageUrl(), new LinkedHashSet<>());
            question.getTests().stream().forEach(test -> {
                TestDTO testDTO = new TestDTO(test.getId(), new LinkedHashSet<>(), 0);

                testDTO.options().add(new OptionDTO(baseUrl + test.getImageUrl1()));
                testDTO.options().add(new OptionDTO(baseUrl + test.getImageUrl2()));
                testDTO.options().add(new OptionDTO(baseUrl + test.getImageUrl3()));
                testDTO.options().add(new OptionDTO(baseUrl + test.getImageUrl4()));
                testDTO.options().add(new OptionDTO(baseUrl + test.getImageUrl5()));

                questionDTO.tests().add(testDTO);
            });
            result.questions().add(questionDTO);
        });
        return result;
    }
}
