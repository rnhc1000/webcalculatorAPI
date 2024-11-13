package br.dev.ferreiras.webcalculatorapi.services;

import br.dev.ferreiras.webcalculatorapi.contracts.IMathOperations;

import java.math.BigDecimal;

public class MultiplicationService implements IMathOperations {

  /**
   * @param operands [0], operands[1]
   * @return multiplication of two operands
   */
  @Override
  public BigDecimal mathOperations(BigDecimal... operands) {

    return operands[0].multiply(operands[1]);
  }
}

