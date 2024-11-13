package br.dev.ferreiras.webcalculatorapi.controller;

import br.dev.ferreiras.webcalculatorapi.dto.OperationCostsDto;
import br.dev.ferreiras.webcalculatorapi.services.OperationCostsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class OperationsController {

  private final OperationCostsService operationCostsService;

  public OperationsController(OperationCostsService operationCostsService) {
    this.operationCostsService = operationCostsService;
  }

  private static final Logger logger = LoggerFactory.getLogger(OperationsController.class);

  @Operation(summary = "Return operators costs")
  @ApiResponse(responseCode = "200", description = "Costs available", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OperationsController.class)))
  @ApiResponse(responseCode = "401", description = "Access denied", content = @Content(mediaType = "application/json"))
  @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content)
  @GetMapping("/costs")
  public ResponseEntity<List<OperationCostsDto>> getOperationCosts() {
    final var costs = operationCostsService.getOperationsCost();

    return ResponseEntity.ok(costs);
  }

}
