package ml.psychology.api.web.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Spatial Recognition Subtest", description = "A subtest of James Barrett test")
@RestController
@RequestMapping("/barrett/{id}/spatial-recognition")
@PreAuthorize("@barrettTestService.isOwner(principal, #id)")
@SecurityRequirement(name = "security_auth")
public class SpatialRecognitionResource {
}
