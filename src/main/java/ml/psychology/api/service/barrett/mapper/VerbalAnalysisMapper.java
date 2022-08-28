package ml.psychology.api.service.barrett.mapper;

import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.VerbalAnalysisAnswer;
import ml.psychology.api.domain.barrett.subtest.VerbalAnalysisSubtest;
import ml.psychology.api.domain.barrett.template.VerbalAnalysisSubTemplate;
import ml.psychology.api.domain.barrett.template.VerbalAnalysisTemplate;
import ml.psychology.api.service.barrett.dto.VerbalAnalysisDTO;
import ml.psychology.api.service.barrett.dto.VerbalAnalysisTestDTO;
import ml.psychology.api.service.barrett.dto.VerbalAnalysisTestGroupDTO;
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

    @Mapping(source = "template.subTemplates", target = "tests")
    VerbalAnalysisTestGroupDTO templateToTestGroupDTO(VerbalAnalysisTemplate template);

    VerbalAnalysisDTO mergeToDto(VerbalAnalysisSubtest subtest,
                                 int requiredTime,
                                 List<VerbalAnalysisTemplate> testGroups,
                                 List<VerbalAnalysisAnswer> answers);

    @AfterMapping
    default void mergeToDto(List<VerbalAnalysisAnswer> answers, @MappingTarget VerbalAnalysisDTO vrDTO) {
        Iterator<VerbalAnalysisTestGroupDTO> testGroup = vrDTO.testGroups().iterator();
        Iterator<VerbalAnalysisAnswer> answer = answers.iterator();
        while (testGroup.hasNext()) {
            Iterator<VerbalAnalysisTestDTO> test = testGroup.next().tests().iterator();
            while (test.hasNext() && answer.hasNext())
                test.next().setUserAnswer(answer.next().getUserAnswer());
        }
    }

    default void mergeToAnswers(
            List<Integer> userAnswers,
            @MappingTarget List<VerbalAnalysisAnswer> answers) {
        Iterator<Integer> dto = userAnswers.iterator();
        Iterator<VerbalAnalysisAnswer> answer = answers.iterator();
        while (answer.hasNext() && dto.hasNext()) {
            answer.next().setUserAnswer(dto.next().byteValue());
        }
    }
}
