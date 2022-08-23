package ml.psychology.api.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ml.psychology.api.service.WaisIvPictureReasoningService;
import ml.psychology.api.service.dto.PictureReasoningDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import java.net.URI;
import java.net.URISyntaxException;

@Tag(name = "WAIS-IV Picture Reasoning", description = "A subtest of WAIS-Iv assessment")
//@RestController
@PreAuthorize("@waisIvAssessmentService.isOwner(principal, #id)")
@RequestMapping("/waisiv/{id}/picture-reasoning")
@SecurityRequirement(name = "security_auth")
public class WaisIvPictureReasoningResource {

    private final Logger log = LoggerFactory.getLogger(WaisIvPictureReasoningResource.class);
    private final WaisIvPictureReasoningService waisIvPictureReasoningService;

    public WaisIvPictureReasoningResource(WaisIvPictureReasoningService waisIvPictureReasoningService) {
        this.waisIvPictureReasoningService = waisIvPictureReasoningService;
    }

    @Operation(
            summary = "${api.waisIvAssessment.createPictureReasoning.description}",
            description = "${api.waisIvAssessment.createPictureReasoning.notes}"
    )
    @Parameters(
            @Parameter(name = "id", description = "WAIS-IV Assessment ID", example = "10000000003")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "${api.responseCodes.created.description}"),
            @ApiResponse(
                    responseCode = "401",
                    description = "${api.responseCodes.unauthorized.description}",
                    content = {@Content(schema = @Schema(hidden = true))}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "${api.responseCodes.forbidden.description}",
                    content = {@Content(schema = @Schema(hidden = true))}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "${api.responseCodes.conflict.description}",
                    content = {@Content(schema = @Schema(hidden = true))}
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/json")
    public ResponseEntity<PictureReasoningDTO> createPictureReasoning(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to create a new WAIS-IV assessment");
        try {
            return ResponseEntity
                    .created(new URI("/waisiv/" + id + "/picture-reasoning"))
                    .body(waisIvPictureReasoningService.create(id));
        } catch (EntityExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}
