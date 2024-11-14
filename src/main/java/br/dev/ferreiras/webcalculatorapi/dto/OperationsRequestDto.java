package br.dev.ferreiras.webcalculatorapi.dto;

import java.math.BigDecimal;

public record OperationsRequestDto(
    BigDecimal operandOne,
    BigDecimal operandTwo,
    String operator,
    String username
) {
}
