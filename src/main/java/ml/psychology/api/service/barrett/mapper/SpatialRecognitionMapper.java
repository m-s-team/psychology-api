package ml.psychology.api.service.barrett.mapper;

import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.SpatialRecognitionAnswer;
import ml.psychology.api.domain.barrett.subtest.SpatialRecognitionSubtest;
import ml.psychology.api.domain.barrett.template.SpatialRecognitionSubTemplate;
import ml.psychology.api.domain.barrett.template.SpatialRecognitionTemplate;
import ml.psychology.api.service.barrett.dto.SpatialRecognitionDTO;
import ml.psychology.api.service.barrett.dto.SpatialRecognitionTestDTO;
import ml.psychology.api.service.barrett.dto.SpatialRecognitionTestGroupDTO;
import ml.psychology.api.service.barrett.dto.answer.SpatialAnswerDTO;
import org.mapstruct.*;

import java.util.Iterator;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SpatialRecognitionMapper {

    List<SpatialRecognitionAnswer> subTemplatesToAnswers(List<SpatialRecognitionSubTemplate> subTemplates, @Context BarrettTest assessment);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "subTemplate", target = "testSubTemplate")
    SpatialRecognitionAnswer templateToAnswer(SpatialRecognitionSubTemplate subTemplate, @Context BarrettTest assessment);

    @AfterMapping
    default void subTemplateToAnswer(@Context BarrettTest assessment, @MappingTarget SpatialRecognitionAnswer answer) {
        answer.setAssessment(assessment);
    }

    @Mapping(target = "id", ignore = true)
    SpatialRecognitionTestDTO subTemplateToTestDTO(SpatialRecognitionSubTemplate subTemplate);

    @Mapping(source = "template.subTemplates", target = "tests")
    SpatialRecognitionTestGroupDTO templateToTestGroupDTO(SpatialRecognitionTemplate template);

    SpatialRecognitionDTO mergeToDto(SpatialRecognitionSubtest subtest,
                                  int requiredTime,
                                  List<SpatialRecognitionTemplate> testGroups,
                                  List<SpatialRecognitionAnswer> answers);

    @AfterMapping
    default void mergeToDto(List<SpatialRecognitionAnswer> answers, @MappingTarget SpatialRecognitionDTO vrDTO) {
        int id = 0;
        Iterator<SpatialRecognitionAnswer> answer = answers.iterator();
        for (SpatialRecognitionTestGroupDTO group: vrDTO.testGroups())
            for (SpatialRecognitionTestDTO test: group.tests()) {
                test.setId(id++);
                test.setUserAnswer(answer.next().getUserAnswer());
            }
    }

    default void mergeToAnswers(List<SpatialAnswerDTO> userAnswers, @MappingTarget List<SpatialRecognitionAnswer> answers) {
        for (SpatialAnswerDTO userAnswer: userAnswers)
            answers.get(userAnswer.getId()).setUserAnswer(userAnswer.getUserAnswer());
    }

}
