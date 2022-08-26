package ml.psychology.api.service.barrett.mapper;

import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.NumericalReasoningAnswer;
import ml.psychology.api.domain.barrett.subtest.NumericalReasoningSubtest;
import ml.psychology.api.domain.barrett.template.NumericalReasoningTemplate;
import ml.psychology.api.service.barrett.dto.NumericalReasoningDTO;
import ml.psychology.api.service.barrett.dto.NumericalReasoningQuestionDTO;
import org.mapstruct.*;

import java.util.Iterator;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NumericalReasoningMapper {

    NumericalReasoningDTO templateToQuestionDto(NumericalReasoningTemplate template);

    List<NumericalReasoningAnswer> templatesToAnswers(List<NumericalReasoningTemplate> templates, @Context BarrettTest assessment);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "template", target = "questionTemplate")
    NumericalReasoningAnswer templateToAnswer(NumericalReasoningTemplate template, @Context BarrettTest assessment);

    @AfterMapping
    default void templateToAnswer(@Context BarrettTest assessment, @MappingTarget NumericalReasoningAnswer answer) {
        answer.setAssessment(assessment);
    }

    NumericalReasoningDTO mergeToDto(NumericalReasoningSubtest subtest,
                                     int requiredTime,
                                     List<NumericalReasoningTemplate> questions,
                                     List<NumericalReasoningAnswer> answers);

    @AfterMapping
    default void mergeToDto(List<NumericalReasoningAnswer> answers, @MappingTarget NumericalReasoningDTO vrDTO) {
        Iterator<NumericalReasoningQuestionDTO> question = vrDTO.questions().iterator();
        Iterator<NumericalReasoningAnswer> answer = answers.iterator();
        while (answer.hasNext() && question.hasNext()) {
            question.next().setUserAnswer(answer.next().getUserAnswer());
        }
    }


    default void mergeToAnswers(
            List<Integer> userAnswers,
            @MappingTarget List<NumericalReasoningAnswer> answers) {
        Iterator<Integer> dto = userAnswers.iterator();
        Iterator<NumericalReasoningAnswer> answer = answers.iterator();
        while (answer.hasNext() && dto.hasNext()) {
            answer.next().setUserAnswer(dto.next().byteValue());
        }
    }
}
