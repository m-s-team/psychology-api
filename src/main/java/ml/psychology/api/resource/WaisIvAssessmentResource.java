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
import ml.psychology.api.domain.WaisIvAssessment;
import ml.psychology.api.resource.dto.WaisIvAssessmentDTO;
import ml.psychology.api.resource.mapper.WaisIvAssessmentMapper;
import ml.psychology.api.service.WaisIvAssessmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "WAIS-IV Assessment", description = "The Wechsler Adult Intelligence Scale (WAIS) Assessments")
@RestController
@RequestMapping("/wais-iv-assessment")
@SecurityRequirement(name = "security_auth")
public class WaisIvAssessmentResource {
    private final Logger log = LoggerFactory.getLogger(WaisIvAssessmentResource.class);
    private final WaisIvAssessmentService waisIvAssessmentService;
    private final WaisIvAssessmentMapper waisIvAssessmentMapper;

    public WaisIvAssessmentResource(WaisIvAssessmentService waisIvAssessmentService, WaisIvAssessmentMapper waisIvAssessmentMapper) {
        this.waisIvAssessmentService = waisIvAssessmentService;
        this.waisIvAssessmentMapper = waisIvAssessmentMapper;
    }

    @Operation(
            summary = "${api.waisIvAssessment.createWaisIvAssessment.description}",
            description = "${api.waisIvAssessment.createWaisIvAssessment.notes}")
    @ApiResponses(value = {
            // TODO: ok -> 201
            @ApiResponse(responseCode = "201", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(
                    responseCode = "401",
                    description = "${api.responseCodes.unauthorized.description}",
                    content = {@Content(schema = @Schema(hidden = true))}
            )
    })
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(produces = "application/json")
    public ResponseEntity<WaisIvAssessmentDTO> createWaisIvAssessmentDTO(Principal principal) throws URISyntaxException {
        log.debug("REST request to create a new WAIS-IV assessment");
        String userId = principal.getName();
        WaisIvAssessmentDTO result = waisIvAssessmentMapper.entityToDto(waisIvAssessmentService.createForUser(userId));
        return ResponseEntity
                .created(new URI("/wais-iv-assessment/" + result.id()))
                .body(result);
    }

    @Operation(
            summary = "${api.waisIvAssessment.getWaisIvAssessment.description}",
            description = "${api.waisIvAssessment.getWaisIvAssessment.notes}"
    )
    @Parameters(
            @Parameter(name = "id", description = "WAIS-IV Assessment ID", example = "10000000003")
    )
    @ApiResponses(value = {
            // TODO: ok -> 204
            @ApiResponse(responseCode = "204", description = "${api.responseCodes.ok.description}"),
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
                    responseCode = "404",
                    description = "${api.responseCodes.notFound.description}",
                    content = {@Content(schema = @Schema(hidden = true))}
            )
    })
    @PreAuthorize("@waisIvAssessmentService.isOwner(principal, #id)")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = "application/json", path = {"/{id}"})
    public WaisIvAssessmentDTO getWaisIvAssessment(@PathVariable Long id) {
        log.debug("REST request to get WAIS-IV assessments : {}", id);
        return waisIvAssessmentService
                .getById(id)
                .map(waisIvAssessmentMapper::entityToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "${api.waisIvAssessment.getAllWaisIvAssessment.description}",
            description = "${api.waisIvAssessment.getAllWaisIvAssessment.notes}"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(
                    responseCode = "401",
                    description = "${api.responseCodes.unauthorized.description}",
                    content = {@Content(schema = @Schema(hidden = true))}
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = "application/json")
    public List<WaisIvAssessmentDTO> getAllWaisIvAssessments(Principal principal) {
        log.debug("REST request to get all WAIS-IV assessments of the user");
        String userId = principal.getName();
        return waisIvAssessmentService.getByUserId(userId).stream()
                .map(waisIvAssessmentMapper::entityToDto).collect(Collectors.toList());
    }

    @Operation(
            summary = "${api.waisIvAssessment.updateWaisIvAssessment.description}",
            description = "${api.waisIvAssessment.updateWaisIvAssessment.notes}"
    )
    @Parameters(
            @Parameter(name = "id", description = "WAIS-IV Assessment ID", example = "10000000003")
    )
    @ApiResponses(value = {
            // TODO: ok -> 204
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(
                    responseCode = "401",
                    description = "${api.responseCodes.unauthorized.description}",
                    content = {@Content(schema = @Schema(hidden = true))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "${api.responseCodes.badRequest.description}",
                    content = {@Content(schema = @Schema(hidden = true))}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "${api.responseCodes.forbidden.description}",
                    content = {@Content(schema = @Schema(hidden = true))}
            ),
            // TODO: 404 response body
            @ApiResponse(
                    responseCode = "404",
                    description = "${api.responseCodes.notFound.description}",
                    content = {@Content(schema = @Schema(hidden = true))}
            )
    })
    @PreAuthorize("@waisIvAssessmentService.isOwner(principal, #id)")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(produces = "application/json", path = {"/{id}"})
    public ResponseEntity<WaisIvAssessmentDTO> updateWaisIvAssessmentDTO(
            @PathVariable Long id,
            @Valid @RequestBody WaisIvAssessmentDTO assessmentDto,
            Principal principal
    ) {
        log.debug("REST request to update WAIS-IV assessment : {}", assessmentDto);
        if (!assessmentDto.id().equals(id) || !assessmentDto.userId().equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID");
        WaisIvAssessment assessment = waisIvAssessmentMapper.dtoToEntity(assessmentDto);
        assessment = waisIvAssessmentService.update(assessment);
        assessmentDto = waisIvAssessmentMapper.entityToDto(assessment);
        return ResponseEntity
                .ok()
                .body(assessmentDto);
    }

    @Operation(
            summary = "${api.waisIvAssessment.deleteWaisIvAssessment.description}",
            description = "${api.waisIvAssessment.deleteWaisIvAssessment.notes}"
    )
    @Parameters(
            @Parameter(name = "id", description = "WAIS-IV Assessment ID", example = "10000000003")
    )
    @ApiResponses(value = {
            // TODO: ok -> 204
            @ApiResponse(responseCode = "204", description = "${api.responseCodes.ok.description}"),
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
                    responseCode = "404",
                    description = "${api.responseCodes.notFound.description}",
                    content = {@Content(schema = @Schema(hidden = true))}
            )
    })
    @PreAuthorize("@waisIvAssessmentService.isOwner(principal, #id)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWaisIvAssessment(@PathVariable Long id) {
        log.debug("REST request to delete WAIS-IV assessment : {}", id);
        waisIvAssessmentService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
