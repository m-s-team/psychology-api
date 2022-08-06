package ml.psychology.api.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public record OptionDTO(
        @NotNull
        @Schema(description = "Image URL of the Option")
        String imageUrl
) {
}
