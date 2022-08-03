package ml.psychology.api.resource.mapper;

import ml.psychology.api.domain.WaisIvAssessment;
import ml.psychology.api.resource.dto.WaisIvAssessmentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WaisIvAssessmentMapper {
    WaisIvAssessmentDTO entityToDto(WaisIvAssessment entity);
    WaisIvAssessment dtoToEntity(WaisIvAssessmentDTO dto);
}
