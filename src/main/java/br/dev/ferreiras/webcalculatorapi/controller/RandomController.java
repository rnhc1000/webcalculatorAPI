package br.dev.ferreiras.webcalculatorapi.controller;

import br.dev.ferreiras.webcalculatorapi.services.RandomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/v2")
public class RandomController {

  private final RandomService randomService;

  public RandomController(RandomService randomService) {
    this.randomService = randomService;
  }

  private static final Logger logger = LoggerFactory.getLogger(RandomController.class);

  @Operation(summary = "Get a random string")
  @ApiResponse(responseCode = "200", description = "Get a random string through random.org", content = @Content)
  @ApiResponse(responseCode = "401", description = "Not Authorized", content = @Content)
  @ApiResponse(responseCode = "404", description = "endpoint not found", content = @Content)
  @ApiResponse(responseCode = "415", description = "media not supported", content = @Content)
  @PostMapping(value = "/random", consumes = {"application/json"})
//  @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
  public ResponseEntity<String> getRandomString() {

    return ResponseEntity.ok(this.randomService.makeApiRequest());
  }
}
