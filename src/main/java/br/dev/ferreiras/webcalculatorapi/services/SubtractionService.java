package br.dev.ferreiras.webcalculatorapi.services;

import br.dev.ferreiras.webcalculatorapi.contracts.IMathOperations;

import java.math.BigDecimal;

public class SubtractionService implements IMathOperations {

  /**
   * @param operands [0], operands[1]
   * @return subtraction of two operands
   */
  @Override
  public BigDecimal mathOperations(BigDecimal... operands) {

    return operands[0].subtract(operands[1]);
  }
}

