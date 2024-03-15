package be.vinci.pae.exception;

public class FatalError extends RuntimeException {

  public FatalError(String message) {
    super(message);
  }

  public FatalError(String message, Throwable cause) {
    super(message, cause);
  }
}

