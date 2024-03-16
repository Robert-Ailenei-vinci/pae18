package be.vinci.pae.exception;

/**
 * Represents an exception that is thrown when a user is not authorised to perform an action.
 */
public class AuthorisationException extends RuntimeException {

  /**
   * Constructs a new AuthorisationException with the specified detail message.
   *
   * @param message the detail message
   */
  public AuthorisationException(String message) {
    super(message);
  }

  /**
   * Constructs a new AuthorisationException with the specified detail message and cause.
   *
   * @param message the detail message
   * @param cause   the cause
   */
  public AuthorisationException(String message, Throwable cause) {
    super(message, cause);
  }
}
