package be.vinci.pae.exception;

/**
 * Represents an exception that is thrown when a business rule is violated.
 */
public class BizException extends RuntimeException {

  public BizException(String message) {
    super(message);
  }

  public BizException(String message, Throwable cause) {
    super(message, cause);
  }
}
