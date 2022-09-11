package ml.psychology.api.domain.barrett.converter;

import ml.psychology.api.domain.barrett.enumeration.SpatialRecognitionAnswerType;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpatialRecognitionAnswerTypeArrayToString implements AttributeConverter<List<SpatialRecognitionAnswerType>, String> {
    @Override
    public String convertToDatabaseColumn(List<SpatialRecognitionAnswerType> attribute) {
        return attribute == null ? null : StringUtils.join(attribute.stream().map(answer -> answer.ordinal()).collect(Collectors.toList()),",");
    }

    @Override
    public List<SpatialRecognitionAnswerType> convertToEntityAttribute(String dbData) {
        if (StringUtils.isBlank(dbData))
            return Collections.emptyList();
        try (Stream<String> stream = Arrays.stream(dbData.split(","))) {
            return stream
                    .map(Integer::parseInt)
                    .map(answer -> SpatialRecognitionAnswerType.values()[answer])
                    .collect(Collectors.toList());
        }
    }
}
