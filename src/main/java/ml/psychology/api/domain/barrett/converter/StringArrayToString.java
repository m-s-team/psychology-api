package ml.psychology.api.domain.barrett.converter;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringArrayToString implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return attribute == null ? null : StringUtils.join(attribute,",");
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (StringUtils.isBlank(dbData))
            return Collections.emptyList();

        try (Stream<String> stream = Arrays.stream(dbData.split(","))) {
            return stream.collect(Collectors.toList());
        }
    }
}
