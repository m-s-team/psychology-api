package ml.psychology.api.service.barrett.mapper;

import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.VisualReasoningAnswer;
import ml.psychology.api.domain.barrett.subtest.VisualReasoningSubtest;
import ml.psychology.api.domain.barrett.template.VisualReasoningTemplate;
import ml.psychology.api.service.barrett.dto.VisualReasoningDTO;
import ml.psychology.api.service.barrett.dto.VisualReasoningTestDTO;
import org.mapstruct.*;

import java.util.Iterator;
import java.util.List;

@Mapper(componentModel = "spring")
public interface VisualReasoningMapper {

    VisualReasoningTestDTO templateToTestDto(VisualReasoningTemplate template);

    List<VisualReasoningAnswer> templatesToAnswers(List<VisualReasoningTemplate> templates, @Context BarrettTest assessment);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "template", target = "testTemplate")
    VisualReasoningAnswer templateToAnswer(VisualReasoningTemplate template, @Context BarrettTest assessment);

    @AfterMapping
    default void templateToAnswer(@Context BarrettTest assessment, @MappingTarget VisualReasoningAnswer answer) {
        answer.setAssessment(assessment);
    }

    VisualReasoningDTO mergeToDto(VisualReasoningSubtest subtest,
                                  int requiredTime,
                                  List<VisualReasoningTemplate> tests,
                                  List<VisualReasoningAnswer> answers);

    @AfterMapping
    default void mergeToDto(List<VisualReasoningAnswer> answers, @MappingTarget VisualReasoningDTO vrDTO) {
        Iterator<VisualReasoningTestDTO> test = vrDTO.tests().iterator();
        Iterator<VisualReasoningAnswer> answer = answers.iterator();
        while (answer.hasNext() && test.hasNext()) {
            test.next().setUserAnswer(answer.next().getUserAnswer());
        }

    }

    default void mergeToAnswers(
            List<Integer> userAnswers,
            @MappingTarget List<VisualReasoningAnswer> answers) {
        Iterator<Integer> dto = userAnswers.iterator();
        Iterator<VisualReasoningAnswer> answer = answers.iterator();
        while (answer.hasNext() && dto.hasNext()) {
            answer.next().setUserAnswer(dto.next().byteValue());
        }
    }

}
