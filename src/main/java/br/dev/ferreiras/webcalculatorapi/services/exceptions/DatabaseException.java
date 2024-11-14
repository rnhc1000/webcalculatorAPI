package br.dev.ferreiras.webcalculatorapi.services.exceptions;

public class DatabaseException extends RuntimeException{
  public DatabaseException(final String message) {
    super(message);
  }
}
