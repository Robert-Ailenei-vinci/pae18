package be.vinci.pae.exception;

/**
 * Represents an exception that is thrown when a supervisor is not found.
 */
public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String message) {
    super(message);
  }

  public UserNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
