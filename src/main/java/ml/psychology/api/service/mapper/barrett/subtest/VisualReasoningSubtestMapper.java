package ml.psychology.api.service.mapper.barrett.subtest;

import ml.psychology.api.domain.barrett.subtest.VisualReasoningSubtest;
import ml.psychology.api.service.dto.barrett.subtest.VisualReasoningSubtestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisualReasoningSubtestMapper {
    VisualReasoningSubtestDTO entityToDto(VisualReasoningSubtest entity);
    VisualReasoningSubtest dtoToEntity(VisualReasoningSubtestDTO dto);
}
