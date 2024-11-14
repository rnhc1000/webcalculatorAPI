package br.dev.ferreiras.webcalculatorapi.dto;

import java.math.BigDecimal;

public record LoadBalanceRequestDto(String username, BigDecimal balance){
}
