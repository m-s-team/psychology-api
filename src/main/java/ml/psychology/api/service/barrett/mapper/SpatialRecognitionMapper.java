package ml.psychology.api.service.barrett.mapper;

import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.SpatialRecognitionAnswer;
import ml.psychology.api.domain.barrett.enumeration.SpatialRecognitionAnswerType;
import ml.psychology.api.domain.barrett.subtest.SpatialRecognitionSubtest;
import ml.psychology.api.domain.barrett.template.SpatialRecognitionTemplate;
import ml.psychology.api.service.barrett.dto.SpatialRecognitionDTO;
import ml.psychology.api.service.barrett.dto.SpatialRecognitionTestDTO;
import org.mapstruct.*;

import java.util.Iterator;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SpatialRecognitionMapper {

    SpatialRecognitionTestDTO templateToTestDto(SpatialRecognitionTemplate template);

    List<SpatialRecognitionAnswer> templatesToAnswers(List<SpatialRecognitionTemplate> templates, @Context BarrettTest assessment);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "template", target = "testTemplate")
    SpatialRecognitionAnswer templateToAnswer(SpatialRecognitionTemplate template, @Context BarrettTest assessment);

    @AfterMapping
    default void templateToAnswer(@Context BarrettTest assessment, @MappingTarget SpatialRecognitionAnswer answer) {
        answer.setAssessment(assessment);
    }

    SpatialRecognitionDTO mergeToDto(SpatialRecognitionSubtest subtest,
                                  int requiredTime,
                                  List<SpatialRecognitionTemplate> tests,
                                  List<SpatialRecognitionAnswer> answers);

    @AfterMapping
    default void mergeToDto(List<SpatialRecognitionAnswer> answers, @MappingTarget SpatialRecognitionDTO vrDTO) {
        Iterator<SpatialRecognitionTestDTO> test = vrDTO.tests().iterator();
        Iterator<SpatialRecognitionAnswer> answer = answers.iterator();
        while (answer.hasNext() && test.hasNext()) {
            test.next().setUserAnswers(answer.next().getUserAnswers());
        }
    }

    default void mergeToAnswers(
            List<List<SpatialRecognitionAnswerType>> userAnswers,
            @MappingTarget List<SpatialRecognitionAnswer> answers) {
        Iterator<List<SpatialRecognitionAnswerType>> dto = userAnswers.iterator();
        Iterator<SpatialRecognitionAnswer> answer = answers.iterator();
        while (answer.hasNext() && dto.hasNext()) {
            answer.next().setUserAnswers(dto.next());
        }
    }

}
