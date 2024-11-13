package br.dev.ferreiras.webcalculatorapi.contracts;

import java.math.BigDecimal;

public interface IMathOperations {
  public BigDecimal mathOperations(BigDecimal... operands);
}
