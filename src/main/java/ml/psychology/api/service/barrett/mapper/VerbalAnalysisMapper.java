package ml.psychology.api.service.barrett.mapper;

import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.VerbalAnalysisAnswer;
import ml.psychology.api.domain.barrett.subtest.VerbalAnalysisSubtest;
import ml.psychology.api.domain.barrett.template.VerbalAnalysisSubTemplate;
import ml.psychology.api.domain.barrett.template.VerbalAnalysisTemplate;
import ml.psychology.api.service.barrett.dto.VerbalAnalysisDTO;
import ml.psychology.api.service.barrett.dto.VerbalAnalysisTestDTO;
import ml.psychology.api.service.barrett.dto.VerbalAnalysisTestGroupDTO;
import ml.psychology.api.service.barrett.dto.answer.VerbalAnswerDTO;
import org.mapstruct.*;

import java.util.Iterator;
import java.util.List;

@Mapper(componentModel = "spring")
public interface VerbalAnalysisMapper {

    List<VerbalAnalysisAnswer> subTemplatesToAnswers(List<VerbalAnalysisSubTemplate> subTemplates, @Context BarrettTest assessment);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "subTemplate", target = "testSubTemplate")
    VerbalAnalysisAnswer subTemplateToAnswer(VerbalAnalysisSubTemplate subTemplate, @Context BarrettTest assessment);

    @AfterMapping
    default void subTemplateToAnswer(@Context BarrettTest assessment, @MappingTarget VerbalAnalysisAnswer answer) {
        answer.setAssessment(assessment);
    }

    @Mapping(target = "id", ignore = true)
    VerbalAnalysisTestDTO subTemplateToTestDTO(VerbalAnalysisSubTemplate subTemplate);

    @Mapping(source = "template.subTemplates", target = "tests")
    VerbalAnalysisTestGroupDTO templateToTestGroupDTO(VerbalAnalysisTemplate template);

    VerbalAnalysisDTO mergeToDto(VerbalAnalysisSubtest subtest,
                                 int requiredTime,
                                 List<VerbalAnalysisTemplate> testGroups,
                                 List<VerbalAnalysisAnswer> answers);

    @AfterMapping
    default void mergeToDto(List<VerbalAnalysisAnswer> answers, @MappingTarget VerbalAnalysisDTO vrDTO) {
        int id = 0;
        Iterator<VerbalAnalysisAnswer> answer = answers.iterator();
        for (VerbalAnalysisTestGroupDTO group: vrDTO.testGroups())
            for (VerbalAnalysisTestDTO test: group.tests()) {
                test.setId(id++);
                test.setUserAnswer(answer.next().getUserAnswer());
            }
    }

    default void mergeToAnswers(List<VerbalAnswerDTO> userAnswers, @MappingTarget List<VerbalAnalysisAnswer> answers) {
        for (VerbalAnswerDTO userAnswer: userAnswers)
            answers.get(userAnswer.getId()).setUserAnswer((byte) userAnswer.getUserAnswer());
    }
}
