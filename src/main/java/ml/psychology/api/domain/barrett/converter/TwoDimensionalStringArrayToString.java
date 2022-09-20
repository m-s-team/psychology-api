package ml.psychology.api.domain.barrett.converter;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TwoDimensionalStringArrayToString implements AttributeConverter<List<List<String>>, String> {
    @Override
    public String convertToDatabaseColumn(List<List<String>> attribute) {
        return StringUtils.join(
                attribute.stream().map(strings -> StringUtils.join(strings, ","))
                .collect(Collectors.toList()), "|");
    }

    public List<List<String>> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split("\\|"))
                .map(s -> Arrays.stream(s.split(",")).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}
