package be.vinci.pae.exception;

/**
 * Represents an exception that is thrown when a supervisor is not found.
 */
public class SupervisorNotFoundException extends RuntimeException {

  public SupervisorNotFoundException(String message) {
    super(message);
  }

  public SupervisorNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
