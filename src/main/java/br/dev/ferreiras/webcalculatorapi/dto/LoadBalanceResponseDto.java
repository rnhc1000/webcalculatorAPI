package br.dev.ferreiras.webcalculatorapi.dto;

import java.math.BigDecimal;

public record LoadBalanceResponseDto(String username, BigDecimal balance) {
}
