package br.dev.ferreiras.webcalculatorapi.services.exceptions;

public class OutOfBalanceException extends RuntimeException{
  public OutOfBalanceException(String message) {
    super(message);
  }
}
