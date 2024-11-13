package br.dev.ferreiras.webcalculatorapi.services;

import br.dev.ferreiras.webcalculatorapi.contracts.IMathOperations;
import br.dev.ferreiras.webcalculatorapi.services.exceptions.InvalidMathRequestException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DivisionService implements IMathOperations {

  /**
   * @param operands [0], operands[1]
   * @return division of two operands
   * @throws InvalidMathRequestException no / by zero
   */
  @Override
  public BigDecimal mathOperations(BigDecimal... operands) {

    try {
      return operands[0].divide(operands[1],4, RoundingMode.CEILING);
    } catch(ArithmeticException exception) {
      throw new InvalidMathRequestException(" / by zero");
    }
  }
}

