package br.dev.ferreiras.webcalculatorapi.services.exceptions;

  public class ForbiddenException extends RuntimeException {
    public ForbiddenException(final String message) {
      super(message);
    }
}
