package ml.psychology.api.service.barrett.mapper;

import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.service.barrett.dto.BarrettTestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BarrettTestMapper {

    BarrettTestDTO entityToDto(BarrettTest entity);
}
