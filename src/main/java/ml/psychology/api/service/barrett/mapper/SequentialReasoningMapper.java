package ml.psychology.api.service.barrett.mapper;

import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.SequentialReasoningAnswer;
import ml.psychology.api.domain.barrett.subtest.SequentialReasoningSubtest;
import ml.psychology.api.domain.barrett.template.SequentialReasoningTemplate;
import ml.psychology.api.service.barrett.dto.SequentialReasoningDTO;
import ml.psychology.api.service.barrett.dto.SequentialReasoningTestDTO;
import ml.psychology.api.service.barrett.dto.answer.SequentialAnswerDTO;
import org.mapstruct.*;

import java.util.Iterator;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SequentialReasoningMapper {
    List<SequentialReasoningAnswer> templatesToAnswers(List<SequentialReasoningTemplate> templates, @Context BarrettTest assessment);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "template", target = "testTemplate")
    SequentialReasoningAnswer templateToAnswer(SequentialReasoningTemplate template, @Context BarrettTest assessment);

    @AfterMapping
    default void templateToAnswer(@Context BarrettTest assessment, @MappingTarget SequentialReasoningAnswer answer) {
        answer.setAssessment(assessment);
    }

    @Mapping(target = "id", ignore = true)
    SequentialReasoningTestDTO templateToTestDto(SequentialReasoningTemplate template);

    SequentialReasoningDTO mergeToDto(SequentialReasoningSubtest subtest,
                                  int requiredTime,
                                  List<SequentialReasoningTemplate> tests,
                                  List<SequentialReasoningAnswer> answers);

    @AfterMapping
    default void mergeToDto(List<SequentialReasoningAnswer> answers, @MappingTarget SequentialReasoningDTO vrDTO) {
        int id = 0;
        Iterator<SequentialReasoningTestDTO> test = vrDTO.tests().iterator();
        while (test.hasNext()) test.next().setId(id++);

        test = vrDTO.tests().iterator();
        Iterator<SequentialReasoningAnswer> answer = answers.iterator();
        while (test.hasNext()) {
            test.next().setUserAnswers(answer.next().getUserAnswers());
        }
    }

    default void mergeToAnswers(List<SequentialAnswerDTO> userAnswers, @MappingTarget List<SequentialReasoningAnswer> answers) {
        for (SequentialAnswerDTO userAnswer: userAnswers)
            answers.get(userAnswer.getId()).setUserAnswers(userAnswer.getUserAnswers());
    }
}
