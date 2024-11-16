package br.dev.ferreiras.webcalculatorapi.controller;

import br.dev.ferreiras.webcalculatorapi.dto.OperationCostsDto;
import br.dev.ferreiras.webcalculatorapi.dto.OperationsRequestDto;
import br.dev.ferreiras.webcalculatorapi.dto.OperationsResponseDto;
import br.dev.ferreiras.webcalculatorapi.services.OperationCostsService;
import br.dev.ferreiras.webcalculatorapi.services.OperationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class OperationsController {

  private final OperationsService operationsService;
  private final OperationCostsService operationCostsService;

  public OperationsController(OperationsService operationsService, OperationCostsService operationCostsService) {
    this.operationsService = operationsService;
    this.operationCostsService = operationCostsService;
  }

  private static final Logger logger = LoggerFactory.getLogger(OperationsController.class);

  @Operation(summary = "Given one or two operands and an operator, returns the result and balance available")

  @ApiResponse(responseCode = "200", description = "Got the result", content = @Content(mediaType = "application/json",
      schema = @Schema(implementation = OperationsController.class)))
  @ApiResponse(responseCode = "401", description = "Not authorized!", content = @Content)
  @ApiResponse(responseCode = "422", description = "Operation not allowed!", content = @Content)

  @ResponseStatus(value = HttpStatus.CREATED)
  @PostMapping("/operations")
  public ResponseEntity<OperationsResponseDto> getResults(@RequestBody OperationsRequestDto operationsRequestDto) {

    OperationsController.logger.info("Received data to do maths...");
    final OperationsResponseDto operationsResult = this.operationsService.executeOperations(
        operationsRequestDto.operandOne(),
        operationsRequestDto.operandTwo(),
        operationsRequestDto.operator(),
        operationsRequestDto.username()
    );

    OperationsController.logger.info("Maths done!..");

    return ResponseEntity.ok(new OperationsResponseDto(operationsResult.result(), operationsResult.balance()));
  }

  @Operation(summary = "Return operators costs")
  @ApiResponse(responseCode = "200", description = "Costs available", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OperationsController.class)))
  @ApiResponse(responseCode = "401", description = "Access denied", content = @Content(mediaType = "application/json"))
  @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content)
  @GetMapping("/operators")
  public ResponseEntity<List<OperationCostsDto>> getOperationCosts() {
    final var costs = operationCostsService.getOperationsCost();

    return ResponseEntity.ok(costs);
  }

}
