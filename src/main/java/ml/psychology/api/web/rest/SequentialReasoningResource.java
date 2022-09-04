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
import ml.psychology.api.service.barrett.SequentialReasoningService;
import ml.psychology.api.service.barrett.dto.SequentialAnswersDTO;
import ml.psychology.api.service.barrett.dto.SequentialReasoningDTO;
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

@Tag(name = "Sequential Reasoning Subtest", description = "A subtest of James Barrett test")
@RestController
@RequestMapping("/barrett/{id}/sequential-reasoning")
@PreAuthorize("@barrettTestService.isOwner(principal, #id)")
@SecurityRequirement(name = "security_auth")
public class SequentialReasoningResource {

    private final Logger log = LoggerFactory.getLogger(SequentialReasoningResource.class);
    private final SequentialReasoningService sequentialReasoningService;

    public SequentialReasoningResource(SequentialReasoningService sequentialReasoningService) {
        this.sequentialReasoningService = sequentialReasoningService;
    }


    @Operation(
            summary = "Create a sequential reasoning subtest of James Barrett test",
            description = "A new sequential reasoning subtest will be created"
    )
    @Parameters(
            @Parameter(name = "id", description = "James Barrett test ID", example = "1000000001")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sequential reasoning subtest created successfully"),
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
                    description = "Conflict with the current state, a sequential reasoning subtest currently exists for the specified James Barrett test",
                    content = {@Content(schema = @Schema(hidden = true))}
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/json")
    public ResponseEntity<SequentialReasoningDTO> createSequentialReasoning(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to create a new sequential reasoning subtest");
        try {
            return ResponseEntity
                    .created(new URI("/barrett/" + id + "/sequential-reasoning"))
                    .body(sequentialReasoningService.create(id));
        } catch (EntityExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Operation(
            summary = "Returns the sequential reasoning subtest of James Barrett test",
            description = "A sequential reasoning subtest of specified James Barrett test will be returned"
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
                    description = "The James Barrett test that specified by id or sequential reasoning subtest not found",
                    content = {@Content(schema = @Schema(hidden = true))}
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = "application/json")
    public SequentialReasoningDTO getSequentialReasoning(@PathVariable Long id) {
        log.debug("REST request to get the sequential reasoning subtest");
        try {
            return sequentialReasoningService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Updates sequential reasoning user's answers and then Returns the sequential reasoning subtest of James Barrett test",
            description = """
                    A sequential reasoning subtest of specified James Barrett test will be returned after updating the answers
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
                    description = "The James Barrett test that specified by id or sequential reasoning subtest not found",
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
    public SequentialReasoningDTO updateSequentialReasoning(@PathVariable Long id, @Valid @RequestBody SequentialAnswersDTO answers) {
        try {
            return sequentialReasoningService.updateUserAnswers(id, answers);
        } catch (TimeLimitExceededException e) {
            throw new ResponseStatusException(HttpStatus.LOCKED);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
