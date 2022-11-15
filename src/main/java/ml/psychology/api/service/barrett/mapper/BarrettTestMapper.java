package ml.psychology.api.service.barrett.mapper;

import ml.psychology.api.config.Constants;
import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.service.barrett.dto.BarrettTestDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BarrettTestMapper {

    BarrettTestDTO entityToDto(BarrettTest entity);

    @AfterMapping
    default void entityToDto(@MappingTarget BarrettTestDTO barrettTestDTO) {
        barrettTestDTO.visualReasoningSubtest().setRequiredTime(Constants.VISUAL_REASONING_REQUIRED_MINUTE);
        barrettTestDTO.numericalReasoningSubtest().setRequiredTime(Constants.NUMERICAL_REASONING_REQUIRED_MINUTE);
        barrettTestDTO.verbalAnalysisSubtest().setRequiredTime(Constants.VERBAL_ANALYSIS_REQUIRED_MINUTE);
        barrettTestDTO.sequentialReasoningSubtest().setRequiredTime(Constants.SEQUENTIAL_REASONING_REQUIRED_MINUTE);
        barrettTestDTO.spatialRecognitionSubtest().setRequiredTime(Constants.SPATIAL_RECOGNITION_REQUIRED_MINUTE);
        barrettTestDTO.threeDSubtest().setRequiredTime(Constants.SPATIAL_RECOGNITION_REQUIRED_MINUTE);
    }
}
