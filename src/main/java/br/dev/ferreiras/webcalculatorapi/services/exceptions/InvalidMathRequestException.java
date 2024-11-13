package br.dev.ferreiras.webcalculatorapi.services.exceptions;

public class InvalidMathRequestException extends RuntimeException{
  public InvalidMathRequestException(String message) {
    super(message);
  }
}
