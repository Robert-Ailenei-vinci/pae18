package be.vinci.pae.exception;

/**
 * Represents an exception that is thrown when a business rule is violated.
 */
public class BizException extends RuntimeException {

  /**
   * Constructs a new BizException with the specified detail message.
   *
   * @param message the detail message. The detail message is saved for later retrieval by the
   *                Throwable.getMessage() method.
   */
  public BizException(String message) {
    super(message);
  }

  /**
   * Constructs a new BizException with the specified detail message and cause.
   *
   * @param message the detail message. The detail message is saved for later retrieval by the
   *                Throwable.getMessage() method.
   * @param cause   the cause (which is saved for later retrieval by the Throwable.getCause()
   *                method). A null value is permitted, and indicates that the cause is nonexistent
   *                or unknown.
   */
  public BizException(String message, Throwable cause) {
    super(message, cause);
  }
}