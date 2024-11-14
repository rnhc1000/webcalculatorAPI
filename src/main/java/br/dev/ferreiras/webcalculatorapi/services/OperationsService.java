package br.dev.ferreiras.webcalculatorapi.services;

import br.dev.ferreiras.webcalculatorapi.dto.OperationsResponseDto;
import br.dev.ferreiras.webcalculatorapi.dto.ResponseRandomDto;
import br.dev.ferreiras.webcalculatorapi.repository.OperationsRepository;
import br.dev.ferreiras.webcalculatorapi.services.exceptions.InvalidMathRequestException;
import br.dev.ferreiras.webcalculatorapi.services.exceptions.OutOfBalanceException;
import br.dev.ferreiras.webcalculatorapi.services.exceptions.RandomProcessingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


/**
 * This service class deal on how to make each operation, math or random string
 *
 * @author ricardo@ferreiras.dev.br
 * @version 1.1.030901
 * @since 08/2024
 */

@Service
public class OperationsService {

  private final UserService userService;
  private final RecordsService recordsService;
  private final OperationCostsService operationCostsService;
  private final RandomService randomService;
  private static final Logger logger = LoggerFactory.getLogger(OperationsService.class);
  private static final String OUT_OF_BALANCE_EXCEPTION = "Insufficient funds to do maths! Reload your wallet!";
  final BigDecimal NEGATIVE_BALANCE = new BigDecimal("8000863390488707.59991366095112916");

  public OperationsService(
      final UserService userService, final RecordsService recordsService,
      final OperationsRepository operationsRepository, OperationCostsService operationCostsService, RandomService randomService) {
    this.userService = userService;
    this.recordsService = recordsService;
    this.operationCostsService = operationCostsService;
    this.randomService = randomService;
  }

  private static final boolean DELETED = false;

  /**
   * @param username requires username - email
   * @param operator define the operator
   * @return random string shows up on screen
   */
  public ResponseRandomDto executeOperations(final String username, final String operator) {

    if (null == operator) new OperationsResponseDto(this.NEGATIVE_BALANCE, BigDecimal.ZERO);

    String result;

    final var user = this.userService.getUsername(username);
    OperationsService.logger.info("User: {}", user.orElseThrow().getUsername());

    BigDecimal balance = this.userService.getBalance(username);
    OperationsService.logger.info("Balance: {}", balance);

    final BigDecimal cost = this.operationCostsService.getOperationCostByOperation(operator);
    OperationsService.logger.info("Cost: {}", cost);

    if (0 <= balance.compareTo(cost)) {

      balance = balance.subtract(cost);
      this.userService.updateBalance(username, balance);

      final String randomApiResponse = this.randomService.makeApiRequest();
      OperationsService.logger.info("ApiRandomResponse: {}", randomApiResponse);

      final ObjectMapper objectMapper = new ObjectMapper();

      try {
        final JsonNode rootNode = objectMapper.readTree(randomApiResponse);
        final JsonNode stringNode = rootNode.path("result").path("random").path("data");

        OperationsService.logger.info("Inside Jackson decoder...");
        OperationsService.logger.info("JsonNode: {}", stringNode.path(0).toPrettyString());

        result = stringNode.path(0).toPrettyString();
        this.recordsService.saveRecordsRandom(username, operator, result, cost);

      } catch (final JsonProcessingException e) {
        throw new RandomProcessingException("JSON Processing error");
      }
      return new ResponseRandomDto(result, balance);
    } else {
      throw new OutOfBalanceException(OperationsService.OUT_OF_BALANCE_EXCEPTION);
    }

  }

  /**
   * @param operandOne first operand
   * @param operandTwo second operand
   * @param operator   operator
   * @param username   username
   * @return string result
   */
  public OperationsResponseDto executeOperations(
      BigDecimal operandOne, BigDecimal operandTwo,
      String operator, final String username) {

    if (operator.isEmpty()) new OperationsResponseDto(this.NEGATIVE_BALANCE, BigDecimal.ZERO);
    BigDecimal result;
    operator = operator.toUpperCase();

    final var user = this.userService.getUsername(username);
    OperationsService.logger.info("user: {}", user.orElseThrow().getUsername());

    var balance = this.userService.getBalance(username);
    OperationsService.logger.info("balance: {}", balance);

    final var cost = this.operationCostsService.getOperationCostByOperation(operator);
    OperationsService.logger.info("cost: {}", cost);

    operandOne = (null == operandOne ? BigDecimal.ZERO : operandOne);
    operandTwo = (null == operandTwo ? BigDecimal.ZERO : operandTwo);

    switch (operator) {

      case "ADDITION" -> {

        if (0 <= balance.compareTo(cost)) {

          balance = balance.subtract(cost);
          this.userService.updateBalance(username, balance);
          final AdditionService addition = new AdditionService();
          result = addition.mathOperations(operandOne, operandTwo);
          this.recordsService.saveRecordsRandom(username, operandOne, operandTwo, operator, result, cost, OperationsService.DELETED);

        } else {

          throw new OutOfBalanceException(OperationsService.OUT_OF_BALANCE_EXCEPTION);
        }
      }

      case "SUBTRACTION" -> {

        if (0 < balance.compareTo(cost)) {

          balance = balance.subtract(cost);
          this.userService.updateBalance(username, balance);
          final SubtractionService subtraction = new SubtractionService();
          result = subtraction.mathOperations(operandOne, operandTwo);
          this.recordsService.saveRecordsRandom(username, operandOne, operandTwo, operator, result, cost, OperationsService.DELETED);

        } else {

          throw new OutOfBalanceException(OperationsService.OUT_OF_BALANCE_EXCEPTION);
        }
      }

      case "MULTIPLICATION" -> {

        if (0 < balance.compareTo(cost)) {

          balance = balance.subtract(cost);
          this.userService.updateBalance(username, balance);
          final MultiplicationService multiplication = new MultiplicationService();
          result = multiplication.mathOperations(operandOne, operandTwo);
          this.recordsService.saveRecordsRandom(username, operandOne, operandTwo, operator, result, cost, OperationsService.DELETED);

        } else {
          throw new OutOfBalanceException(OperationsService.OUT_OF_BALANCE_EXCEPTION);
        }
      }

      case "DIVISION" -> {

        if (0 == operandTwo.compareTo(BigDecimal.ZERO)) {
          OperationsService.logger.info("Comparison ok-> Negative Number, {}", operandTwo);
          throw new InvalidMathRequestException("Illegal math operation!");
        }

        if (0 <= balance.compareTo(cost)) {

          balance = balance.subtract(cost);
          this.userService.updateBalance(username, balance);
          final DivisionService division = new DivisionService();
          result = division.mathOperations(operandOne, operandTwo);
          this.recordsService.saveRecordsRandom(username, operandOne, operandTwo, operator, result, cost, OperationsService.DELETED);

        } else {

          OperationsService.logger.info("Result -> Negative Number, {}", operandTwo);
          throw new OutOfBalanceException(OperationsService.OUT_OF_BALANCE_EXCEPTION);

        }
      }

      case "SQUARE_ROOT" -> {

        if (0 > operandOne.compareTo(BigDecimal.ZERO)) {

          throw new InvalidMathRequestException("Illegal math operation!");
        }

        if (0 <= balance.compareTo(cost)) {
          balance = balance.subtract(cost);
          this.userService.updateBalance(username, balance);
          final SquareRootService squareRoot = new SquareRootService();

          result = squareRoot.mathOperations(operandOne);
          this.recordsService.saveRecordsRandom(username, operandOne, operandTwo, operator, result, cost, OperationsService.DELETED);


        } else {

          throw new OutOfBalanceException(OperationsService.OUT_OF_BALANCE_EXCEPTION);

        }
      }

      default -> result = BigDecimal.ZERO;

    }

    return new  OperationsResponseDto(result, balance);
}

}
