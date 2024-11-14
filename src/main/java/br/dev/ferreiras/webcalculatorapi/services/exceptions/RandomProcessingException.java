package br.dev.ferreiras.webcalculatorapi.services.exceptions;

public class RandomProcessingException extends RuntimeException{

  public RandomProcessingException(final String message) {
    super(message);
  }
}
