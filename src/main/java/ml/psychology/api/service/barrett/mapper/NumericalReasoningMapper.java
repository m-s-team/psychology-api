package ml.psychology.api.service.barrett.mapper;

import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.domain.barrett.answer.NumericalReasoningAnswer;
import ml.psychology.api.domain.barrett.subtest.NumericalReasoningSubtest;
import ml.psychology.api.domain.barrett.template.NumericalReasoningTemplate;
import ml.psychology.api.service.barrett.dto.NumericalReasoningDTO;
import ml.psychology.api.service.barrett.dto.NumericalReasoningQuestionDTO;
import ml.psychology.api.service.barrett.dto.answer.NumericalAnswerDTO;
import org.mapstruct.*;

import java.util.Iterator;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NumericalReasoningMapper {

    List<NumericalReasoningAnswer> templatesToAnswers(List<NumericalReasoningTemplate> templates, @Context BarrettTest assessment);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "template", target = "questionTemplate")
    NumericalReasoningAnswer templateToAnswer(NumericalReasoningTemplate template, @Context BarrettTest assessment);

    @AfterMapping
    default void templateToAnswer(@Context BarrettTest assessment, @MappingTarget NumericalReasoningAnswer answer) {
        answer.setAssessment(assessment);
    }

    @Mapping(target = "id", ignore = true)
    NumericalReasoningQuestionDTO templateToQuestionDto(NumericalReasoningTemplate template);

    NumericalReasoningDTO mergeToDto(NumericalReasoningSubtest subtest,
                                     int requiredTime,
                                     List<NumericalReasoningTemplate> questions,
                                     List<NumericalReasoningAnswer> answers);

    @AfterMapping
    default void mergeToDto(List<NumericalReasoningAnswer> answers, @MappingTarget NumericalReasoningDTO vrDTO) {
        Long id = 0L;
        Iterator<NumericalReasoningQuestionDTO> question = vrDTO.questions().iterator();
        while (question.hasNext()) question.next().setId(id++);

        question = vrDTO.questions().iterator();
        Iterator<NumericalReasoningAnswer> answer = answers.iterator();
        while (question.hasNext()) {
            question.next().setUserAnswer(answer.next().getUserAnswer());
        }
    }


    default void mergeToAnswers(List<NumericalAnswerDTO> userAnswers, @MappingTarget List<NumericalReasoningAnswer> answers) {
        for (NumericalAnswerDTO userAnswer: userAnswers)
            answers.get(userAnswer.getId()).setUserAnswer(userAnswer.getUserAnswer());
    }
}
