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
import ml.psychology.api.service.barrett.BarrettTestService;
import ml.psychology.api.service.barrett.dto.BarrettTestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;

@Tag(name = "James Barrett Test", description = "An assessment to finds aptitudes and intelligence")
@RestController
@RequestMapping("/barrett")
@SecurityRequirement(name = "security_auth")
public class BarrettTestResource {

    private final Logger log = LoggerFactory.getLogger(BarrettTestResource.class);
    private final BarrettTestService barrettTestService;

    public BarrettTestResource(BarrettTestService barrettTestService) {
        this.barrettTestService = barrettTestService;
    }

    @Operation(
            summary = "Create a new James Barrett test for the current user",
            description = "A new James Barrett test will be created with ID of the user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "James Barrett test created successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "The user isn't logged in",
                    content = {@Content(schema = @Schema(hidden = true))}
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/json")
    public ResponseEntity<BarrettTestDTO> createBarrettTest(Principal principal) throws URISyntaxException {
        log.debug("REST request to create a new James Barrett test");
        String userId = principal.getName();
        BarrettTestDTO result = barrettTestService.createForUser(userId);
        return ResponseEntity
                .created(new URI("/barrett/" + result.id()))
                .body(result);
    }

    @Operation(
            summary = "Returns an array of James Barrett tests of the current user",
            description = "A List of James Barrett tests will be returned"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(
                    responseCode = "401",
                    description = "The user isn't logged in",
                    content = {@Content(schema = @Schema(hidden = true))}
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = "application/json")
    public List<BarrettTestDTO> getBarrettTests(Principal principal) {
        log.debug("REST request to get all James Barrett tests of the user");
        String userId = principal.getName();
        return barrettTestService.getByUserId(userId);
    }

    @Operation(
            summary = "Delete the specified James Barrett test",
            description = "The specified James Barrett test will be deleted"
    )
    @Parameters(
            @Parameter(name = "id", description = "James Barrett test ID", example = "1000000003")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content, content deleted successfully"),
            @ApiResponse(
                    responseCode = "401",
                    description = "The user isn't logged in",
                    content = {@Content(schema = @Schema(hidden = true))}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access is permanently forbidden, you have no access to this resource",
                    content = {@Content(schema = @Schema(hidden = true))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found, the specified James Barrett test does not exist",
                    content = {@Content(schema = @Schema(hidden = true))}
            )
    })
    @PreAuthorize("@barrettTestService.isOwner(principal, #id)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBarrettTests(@PathVariable Long id) {
        log.debug("REST request to delete James Barrett test : {}", id);
        barrettTestService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
