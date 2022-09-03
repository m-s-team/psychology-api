package ml.psychology.api.service.barrett.mapper;

import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.SequentialReasoningAnswer;
import ml.psychology.api.domain.barrett.subtest.SequentialReasoningSubtest;
import ml.psychology.api.domain.barrett.template.SequentialReasoningTemplate;
import ml.psychology.api.service.barrett.dto.SequentialReasoningDTO;
import ml.psychology.api.service.barrett.dto.SequentialReasoningTestDTO;
import org.mapstruct.*;

import java.util.Iterator;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SequentialReasoningMapper {
    SequentialReasoningTestDTO templateToTestDto(SequentialReasoningTemplate template);

    List<SequentialReasoningAnswer> templatesToAnswers(List<SequentialReasoningTemplate> templates, @Context BarrettTest assessment);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "template", target = "testTemplate")
    SequentialReasoningAnswer templateToAnswer(SequentialReasoningTemplate template, @Context BarrettTest assessment);

    @AfterMapping
    default void templateToAnswer(@Context BarrettTest assessment, @MappingTarget SequentialReasoningAnswer answer) {
        answer.setAssessment(assessment);
    }

    SequentialReasoningDTO mergeToDto(SequentialReasoningSubtest subtest,
                                  int requiredTime,
                                  List<SequentialReasoningTemplate> tests,
                                  List<SequentialReasoningAnswer> answers);

    @AfterMapping
    default void mergeToDto(List<SequentialReasoningAnswer> answers, @MappingTarget SequentialReasoningDTO vrDTO) {
        Iterator<SequentialReasoningTestDTO> test = vrDTO.tests().iterator();
        Iterator<SequentialReasoningAnswer> answer = answers.iterator();
        while (answer.hasNext() && test.hasNext()) {
            test.next().setUserAnswers(answer.next().getUserAnswers());
        }
    }

    default void mergeToAnswers(
            List<List<Integer>> userAnswers,
            @MappingTarget List<SequentialReasoningAnswer> answers) {
        Iterator<List<Integer>> dto = userAnswers.iterator();
        Iterator<SequentialReasoningAnswer> answer = answers.iterator();
        while (answer.hasNext() && dto.hasNext()) {
            answer.next().setUserAnswers(dto.next());
        }
    }
}
