package br.dev.ferreiras.webcalculatorapi.services.exceptions;

public class NoSuchElementException extends RuntimeException{
  public NoSuchElementException(String message) {
    super(message);
  }
}
