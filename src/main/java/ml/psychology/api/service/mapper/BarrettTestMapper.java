package ml.psychology.api.service.mapper;

import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.VisualReasoningAnswer;
import ml.psychology.api.domain.barrett.subtest.VisualReasoningSubtest;
import ml.psychology.api.domain.barrett.template.VisualReasoningTemplate;
import ml.psychology.api.service.dto.barrett.BarrettTestDTO;
import ml.psychology.api.service.dto.barrett.VisualReasoningDTO;
import ml.psychology.api.service.dto.barrett.VisualReasoningSubtestDTO;
import ml.psychology.api.service.dto.barrett.VisualReasoningTestDTO;
import org.mapstruct.*;

import java.util.Iterator;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BarrettTestMapper {

    BarrettTestDTO barrettTestToBarrettTestDto(BarrettTest entity);

    VisualReasoningTestDTO mergeToVisualReasoningTestDto(VisualReasoningTemplate template);

    @Mapping(source = "templates", target = ".")
    List<VisualReasoningAnswer> templatesToAnswers(List<VisualReasoningTemplate> templates, @Context BarrettTest assessment);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "template", target = "testTemplate")
    VisualReasoningAnswer templateToAnswer(VisualReasoningTemplate template, @Context BarrettTest assessment);

    @AfterMapping
    default void templateToAnswer(@Context BarrettTest assessment, @MappingTarget VisualReasoningAnswer answer) {
        answer.setAssessment(assessment);
    }

    VisualReasoningDTO mergeToVisualReasoningDto(VisualReasoningSubtest subtest,
                                                 int requiredTime,
                                                 List<VisualReasoningTemplate> tests,
                                                 List<VisualReasoningAnswer> answers);

    @AfterMapping
    default void mergeToVisualReasoningDto(List<VisualReasoningAnswer> answers, @MappingTarget VisualReasoningDTO vrDTO) {
        Iterator<VisualReasoningTestDTO> test = vrDTO.tests().iterator();
        Iterator<VisualReasoningAnswer> answer = answers.iterator();
        while (answer.hasNext() && test.hasNext()) {
            test.next().setUserAnswer(answer.next().getUserAnswer());
        }

    }

    VisualReasoningSubtestDTO visualReasoningSubtestToVisualReasoningSubtestDto(VisualReasoningSubtest entity);

    default void mergeToVisualReasoningAnswers(
            List<Integer> userAnswers,
            @MappingTarget List<VisualReasoningAnswer> answers) {
        Iterator<Integer> dto = userAnswers.iterator();
        Iterator<VisualReasoningAnswer> answer = answers.iterator();
        while (answer.hasNext() && dto.hasNext()) {
            answer.next().setUserAnswer(dto.next().byteValue());
        }
    }
}
