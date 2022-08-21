package ml.psychology.api.service.mapper.barrett;

import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.service.dto.barrett.BarrettTestDTO;
import ml.psychology.api.service.mapper.barrett.subtest.VisualReasoningSubtestMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        VisualReasoningSubtestMapper.class
})
public interface BarrettTestMapper {

    BarrettTestDTO entityToDto(BarrettTest entity);
    BarrettTest dtoToEntity(BarrettTestDTO dto);
}
