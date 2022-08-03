package ml.psychology.api.resource.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ml.psychology.api.domain.enumeration.WaisIvSubTestStatus;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Schema
public record WaisIvAssessmentDTO(

        @Id
        @Schema(description = "ID of WAIS-IV Assessment", example = "10000000123")
        Long id,

        @NotNull
        @Schema(description = "Creation Time of this WAIS-IV Assessment", example = "2022-08-03T02:32:30.100603994Z")
        Instant createdDate,

        @NotNull
        @Schema(description = "Status of Picture Reasoning Sub-Test", defaultValue = "None")
        WaisIvSubTestStatus pictureReasoningStatus,

        @Schema(description = "Request Time of the Picture Reasoning Sub-Test", example = "2022-08-03T02:32:30.100603994Z")
        Instant pictureReasoningRequestedDate,

        @NotNull
        @Schema(description = "The Owner of This Assessment", example = "google-oauth2|120408978933119965034")
        String userId
) {
}
