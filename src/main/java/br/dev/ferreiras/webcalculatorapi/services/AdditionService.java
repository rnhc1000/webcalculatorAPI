package br.dev.ferreiras.webcalculatorapi.services;

import br.dev.ferreiras.webcalculatorapi.contracts.IMathOperations;

import java.math.BigDecimal;

public class AdditionService implements IMathOperations {

  /**
   * @param operands [0], operands[1]
   * @return sum of two operands
   */
  @Override
  public BigDecimal mathOperations(BigDecimal... operands) {

    return operands[0].add(operands[1]);
  }
}

