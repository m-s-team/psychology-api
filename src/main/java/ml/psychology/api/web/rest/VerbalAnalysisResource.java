package ml.psychology.api.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ml.psychology.api.service.barrett.VerbalAnalysisService;
import ml.psychology.api.service.barrett.dto.TestAnswersDTO;
import ml.psychology.api.service.barrett.dto.VerbalAnalysisDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.TimeLimitExceededException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@Tag(name = "Verbal Analysis Subtest", description = "A subtest of James Barrett test")
@RestController
@RequestMapping("/barrett/{id}/verbal-analysis")
@PreAuthorize("@barrettTestService.isOwner(principal, #id)")
@SecurityRequirement(name = "security_auth")
public class VerbalAnalysisResource {

    private final Logger log = LoggerFactory.getLogger(VerbalAnalysisResource.class);
    private final VerbalAnalysisService verbalAnalysisService;

    public VerbalAnalysisResource(VerbalAnalysisService verbalAnalysisService) {
        this.verbalAnalysisService = verbalAnalysisService;
    }

    @Operation(
            summary = "Create a verbal analysis subtest of James Barrett test",
            description = "A new verbal analysis subtest will be created"
    )
    @Parameters(
            @Parameter(name = "id", description = "James Barrett test ID", example = "1000000001")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Verbal analysis subtest created successfully"),
            @ApiResponse(
                    responseCode = "401",
                    description = "The user isn't logged in",
                    content = {@Content(schema = @Schema(hidden = true))}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access is permanently forbidden, the user isn't the owner of this James Barrett test that specified by the id}",
                    content = {@Content(schema = @Schema(hidden = true))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The James Barrett test that specified by id not found",
                    content = {@Content(schema = @Schema(hidden = true))}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict with the current state, a verbal analysis subtest currently exists for the specified James Barrett test",
                    content = {@Content(schema = @Schema(hidden = true))}
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/json")
    public ResponseEntity<VerbalAnalysisDTO> createVerbalAnalysis(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to create a new verbal analysis subtest");
        try {
            return ResponseEntity
                    .created(new URI("/barrett/" + id + "/verbal-analysis"))
                    .body(verbalAnalysisService.create(id));
        } catch (EntityExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Operation(
            summary = "Returns the verbal analysis subtest of James Barrett test",
            description = "A verbal analysis subtest of specified James Barrett test will be returned"
    )
    @Parameters(
            @Parameter(name = "id", description = "James Barrett test ID", example = "1000000001")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(
                    responseCode = "401",
                    description = "The user isn't logged in",
                    content = {@Content(schema = @Schema(hidden = true))}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access is permanently forbidden, the user isn't the owner of this James Barrett test that specified by the id}",
                    content = {@Content(schema = @Schema(hidden = true))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The James Barrett test that specified by id or visual reasoning subtest not found",
                    content = {@Content(schema = @Schema(hidden = true))}
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = "application/json")
    public VerbalAnalysisDTO getVerbalAnalysis(@PathVariable Long id) {
        log.debug("REST request to get the verbal analysis subtest");
        try {
            return verbalAnalysisService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Updates verbal analysis user's answers and then Returns the visual reasoning subtest of James Barrett test",
            description = """
                    A visual reasoning subtest of specified James Barrett test will be returned after updating the answers
                    > Note: The time to send the user's answers must be before the test timeout"""
    )
    @Parameters(
            @Parameter(name = "id", description = "James Barrett test ID", example = "1000000001")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(
                    responseCode = "401",
                    description = "The user isn't logged in",
                    content = {@Content(schema = @Schema(hidden = true))}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access is permanently forbidden, the user isn't the owner of this James Barrett test that specified by the id",
                    content = {@Content(schema = @Schema(hidden = true))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The James Barrett test that specified by id or verbal analysis subtest not found",
                    content = {@Content(schema = @Schema(hidden = true))}
            ),
            @ApiResponse(
                    // TODO: Find the right error
                    responseCode = "423",
                    description = "It's not allowed to change the answers after the test timeout",
                    content = {@Content(schema = @Schema(hidden = true))}
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(produces = "application/json")
    public VerbalAnalysisDTO updateVerbalAnalysis(@PathVariable Long id, @Valid @RequestBody TestAnswersDTO answers) {
        try {
            return verbalAnalysisService.updateUserAnswers(id, answers);
        } catch (TimeLimitExceededException e) {
            throw new ResponseStatusException(HttpStatus.LOCKED);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
