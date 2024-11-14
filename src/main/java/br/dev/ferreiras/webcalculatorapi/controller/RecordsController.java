package br.dev.ferreiras.webcalculatorapi.controller;

import br.dev.ferreiras.webcalculatorapi.dto.RecordsDto;
import br.dev.ferreiras.webcalculatorapi.services.RecordsService;
import br.dev.ferreiras.webcalculatorapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v2")
public class RecordsController {

  private final RecordsService recordsService;

  private final UserService userService;

  private static final String USERNAME_NOT_FOUND = "Username not found:";

  public RecordsController(final RecordsService recordsService, final UserService userService) {
    this.recordsService = recordsService;
    this.userService = userService;
  }

  public static final Logger logger = LoggerFactory.getLogger(RecordsController.class);

  @Operation(summary = "Fetch 10 records per page")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Get up to 10 messages per page.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = RecordsController.class))})})
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/records")
  public ResponseEntity<RecordsDto> getAllMessages(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "20") int size) {

    RecordsController.logger.info("Page Number -> {}, Size of Each Page -> {}", page, size);

    return this.recordsService.getPagedRecords(page, size);
  }

  @Operation(summary = "Fetch 12 records per page provided the username authenticated")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Get up to 12 messages per page.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = RecordsController.class))}),
      @ApiResponse(responseCode = "404", description = "Resource not found!",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = RecordsController.class))})
  })
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/user/records")
  public ResponseEntity<RecordsDto> getRecordsByUsername(
      @RequestParam(defaultValue = "0") final int page,
      @RequestParam(defaultValue = "10") final int size
  ) throws UsernameNotFoundException {

    final String user = Optional.ofNullable(this.userService.authenticated())
        .orElseThrow(() ->
            new UsernameNotFoundException(USERNAME_NOT_FOUND));
    final boolean isDeleted = false;
    RecordsController.logger.info("Page Number: {} , Size of Each Page: {} , User: {}, Deleted? {}", page, size, user, isDeleted);

    return this.recordsService.findRecordsByUsername(page, size, user);
  }

  @Operation(summary = "Fetch 12 records per page provided the username authenticated")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Get up to 12 messages per page.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = RecordsController.class))}),
      @ApiResponse(responseCode = "404", description = "Resource not found!",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = RecordsController.class))})
  })
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/user/operations")
  public ResponseEntity<List<RecordsDto>> getRecordsByUsernameStatus(
      @RequestParam(defaultValue = "0") final int page,
      @RequestParam(defaultValue = "10") final int size,
      @RequestParam(defaultValue = "false") boolean isDeleted
  ) throws Exception {

    final String user = Optional.ofNullable(this.userService.authenticated())
        .orElseThrow(() ->
            new UsernameNotFoundException(USERNAME_NOT_FOUND));
    isDeleted = false;
    RecordsController.logger.info("Page Number: {} , Size of Each Page: {} , User: {},  isDeleted: {}", page, size, user, isDeleted);

    return ResponseEntity.ok(this.recordsService.findRecordsByUsernameStatus(page, size, user));
  }

  @Operation(summary = "Soft delete a record")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Record soft deleted @database.",
          content = {@Content(mediaType = "application/json")})})
  @ResponseStatus(HttpStatus.ACCEPTED)
  @PutMapping("/user/operations/{id}")
  public ResponseEntity<HttpStatus> updateRecord(@PathVariable("id") final Long id) {

    this.recordsService.deleteRecordById(id);

    return ResponseEntity.ok(HttpStatus.ACCEPTED);
  }

  @Operation(summary = "Fetch records soft-deleted provided the username authenticated")
  @ApiResponse(responseCode = "200", description = "Return soft deleted records.",
      content = {@Content(mediaType = "application/json",
          schema = @Schema(implementation = RecordsController.class))})
  @ApiResponse(responseCode = "404", description = "Resource not found!",
      content = @Content)
  @GetMapping(value = "/deleted")
  public ResponseEntity<List<RecordsDto>> getSoftDeletedRecords(
      @RequestParam final int page,
      @RequestParam final int size,
      @RequestParam final String username
  ) throws UsernameNotFoundException{

    final String user = Optional.ofNullable(this.userService.authenticated())
        .orElseThrow(() ->
            new UsernameNotFoundException(USERNAME_NOT_FOUND));
    RecordsController.logger.info("Page Number: {} , Size of Each Page: {} , User: {}", page, size, user);

    return ResponseEntity.ok(this.recordsService.findSoftDeletedRecords(page, size, user));
  }
}


