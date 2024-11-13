package br.dev.ferreiras.webcalculatorapi.services;

import br.dev.ferreiras.webcalculatorapi.contracts.IMathOperations;
import br.dev.ferreiras.webcalculatorapi.services.exceptions.InvalidMathRequestException;

import java.math.BigDecimal;
import java.math.MathContext;

public class SquareRootService implements IMathOperations {

  /**
   * @param operands [0]
   * @return square root of operands[0]
   * @throws InvalidMathRequestException no negative numbers supported
   */
  @Override
  public BigDecimal mathOperations(BigDecimal... operands) {

    final MathContext mathContext = new MathContext(4);
    BigDecimal square = BigDecimal.ZERO;
    try {

      square = operands[0].sqrt(mathContext);

    } catch (final ArithmeticException ex) {

      throw new InvalidMathRequestException("Only positive numbers");

    }

    return square;
  }
}

