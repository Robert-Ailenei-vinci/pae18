package be.vinci.pae.exception;

/**
 * Represents an exception that is thrown when a stage is not found.
 */
public class StageNotFoundException extends RuntimeException {

  public StageNotFoundException(String message) {
    super(message);
  }

  public StageNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
