package be.vinci.pae.exception;

/**
 * Represents an exception that is thrown when a fatal error occurs.
 */
public class FatalError extends RuntimeException {

  public FatalError(String message) {
    super(message);
  }

  public FatalError(String message, Throwable cause) {
    super(message, cause);
  }
}

