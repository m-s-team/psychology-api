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
import ml.psychology.api.service.barrett.NumericalReasoningService;
import ml.psychology.api.service.barrett.dto.NumericalAnswersDTO;
import ml.psychology.api.service.barrett.dto.NumericalReasoningDTO;
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

@Tag(name = "Numerical Reasoning Subtest", description = "A subtest of James Barrett test")
@RestController
@RequestMapping("/barrett/{id}/numerical-reasoning")
@PreAuthorize("@barrettTestService.isOwner(principal, #id)")
@SecurityRequirement(name = "security_auth")
public class NumericalReasoningResource {

    private final Logger log = LoggerFactory.getLogger(NumericalReasoningResource.class);
    private final NumericalReasoningService numericalReasoningService;

    public NumericalReasoningResource(NumericalReasoningService numericalReasoningService) {
        this.numericalReasoningService = numericalReasoningService;
    }

    @Operation(
            summary = "Create a numerical reasoning subtest of James Barrett test",
            description = "A new numerical reasoning subtest will be created"
    )
    @Parameters(
            @Parameter(name = "id", description = "James Barrett test ID", example = "1000000001")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Numerical reasoning subtest created successfully"),
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
                    description = "Conflict with the current state, a numerical reasoning subtest currently exists for the specified James Barrett test",
                    content = {@Content(schema = @Schema(hidden = true))}
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/json")
    public ResponseEntity<NumericalReasoningDTO> createVisualReasoning(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to create a new numerical reasoning subtest");
        try {
            return ResponseEntity
                    .created(new URI("/barrett/" + id + "/numerical-reasoning"))
                    .body(numericalReasoningService.create(id));
        } catch (EntityExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Operation(
            summary = "Returns the numerical reasoning subtest of James Barrett test",
            description = "A numerical reasoning subtest of specified James Barrett test will be returned"
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
                    description = "The James Barrett test that specified by id or numerical reasoning subtest not found",
                    content = {@Content(schema = @Schema(hidden = true))}
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = "application/json")
    public NumericalReasoningDTO getNumericalReasoning(@PathVariable Long id) {
        log.debug("REST request to get the numerical reasoning subtest");
        try {
            return numericalReasoningService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Updates numerical reasoning user's answers and then Returns the numerical reasoning subtest of James Barrett test",
            description = """
                    A numerical reasoning subtest of specified James Barrett test will be returned after updating the answers
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
                    description = "The James Barrett test that specified by id or numerical reasoning subtest not found",
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
    public NumericalReasoningDTO updateNumericalReasoning(@PathVariable Long id, @Valid @RequestBody NumericalAnswersDTO answers) {
        try {
            return numericalReasoningService.updateUserAnswers(id, answers);
        } catch (TimeLimitExceededException e) {
            throw new ResponseStatusException(HttpStatus.LOCKED);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
