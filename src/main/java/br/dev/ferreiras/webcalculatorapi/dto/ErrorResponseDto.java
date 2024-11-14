package br.dev.ferreiras.webcalculatorapi.dto;

import java.time.Instant;

public record ErrorResponseDto(int httpCode, String message, Instant timeStamp) {
}
