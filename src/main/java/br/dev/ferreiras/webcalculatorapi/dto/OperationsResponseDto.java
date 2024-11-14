package br.dev.ferreiras.webcalculatorapi.dto;

import java.math.BigDecimal;

public record OperationsResponseDto(BigDecimal result, BigDecimal balance) {
}
