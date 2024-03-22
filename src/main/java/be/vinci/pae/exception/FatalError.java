package be.vinci.pae.exception;

/**
 * Represents an exception that is thrown when a fatal error occurs. This is a type of unchecked
 * exception that is used to indicate serious problems that a reasonable application should not try
 * to catch.
 */
public class FatalError extends RuntimeException {

  /**
   * Constructs a new FatalError with the specified detail message.
   *
   * @param message the detail message. The detail message is saved for later retrieval by the
   *                Throwable.getMessage() method.
   */
  public FatalError(String message) {
    super(message);
  }

  /**
   * Constructs a new FatalError with the specified detail message and cause.
   *
   * @param message the detail message. The detail message is saved for later retrieval by the
   *                Throwable.getMessage() method.
   * @param cause   the cause (which is saved for later retrieval by the Throwable.getCause()
   *                method). A null value is permitted, and indicates that the cause is nonexistent
   *                or unknown.
   */
  public FatalError(String message, Throwable cause) {
    super(message, cause);
  }
}