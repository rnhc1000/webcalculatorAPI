package br.dev.ferreiras.webcalculatorapi.dto;

import java.math.BigDecimal;

public record ResponseRandomDto(String random, BigDecimal balance) {
}
