package ml.psychology.api.service.barrett.mapper;

import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.VisualReasoningAnswer;
import ml.psychology.api.domain.barrett.subtest.VisualReasoningSubtest;
import ml.psychology.api.domain.barrett.template.VisualReasoningTemplate;
import ml.psychology.api.service.barrett.dto.VisualReasoningDTO;
import ml.psychology.api.service.barrett.dto.VisualReasoningTestDTO;
import ml.psychology.api.service.barrett.dto.answer.VisualAnswerDTO;
import org.mapstruct.*;

import java.util.Iterator;
import java.util.List;

@Mapper(componentModel = "spring")
public interface VisualReasoningMapper {

    List<VisualReasoningAnswer> templatesToAnswers(List<VisualReasoningTemplate> templates, @Context BarrettTest assessment);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "template", target = "testTemplate")
    VisualReasoningAnswer templateToAnswer(VisualReasoningTemplate template, @Context BarrettTest assessment);

    @AfterMapping
    default void templateToAnswer(@Context BarrettTest assessment, @MappingTarget VisualReasoningAnswer answer) {
        answer.setAssessment(assessment);
    }

    @Mapping(target = "id", ignore = true)
    VisualReasoningTestDTO templateToTestDto(VisualReasoningTemplate template);

    VisualReasoningDTO mergeToDto(VisualReasoningSubtest subtest,
                                  int requiredTime,
                                  List<VisualReasoningTemplate> tests,
                                  List<VisualReasoningAnswer> answers);

    @AfterMapping
    default void mergeToDto(List<VisualReasoningAnswer> answers, @MappingTarget VisualReasoningDTO vrDTO) {
        int id = 0;
        Iterator<VisualReasoningTestDTO> test = vrDTO.tests().iterator();
        while (test.hasNext()) test.next().setId(id++);

        test = vrDTO.tests().iterator();
        Iterator<VisualReasoningAnswer> answer = answers.iterator();
        while (test.hasNext()) {
            test.next().setUserAnswer(answer.next().getUserAnswer());
        }
    }

    default void mergeToAnswers(List<VisualAnswerDTO> userAnswers, @MappingTarget List<VisualReasoningAnswer> answers) {

        for (VisualAnswerDTO userAnswer: userAnswers)
            answers.get(userAnswer.getId()).setUserAnswer((byte) userAnswer.getUserAnswer());
    }

}
