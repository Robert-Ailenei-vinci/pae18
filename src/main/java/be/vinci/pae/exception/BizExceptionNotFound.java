package be.vinci.pae.exception;

/**
 * Exception when a resource doesn't exist in biz.
 */
public class BizExceptionNotFound extends RuntimeException {

  /**
   * Exception when a resource doesn't exist in biz.
   *
   * @param message the message of the excep.
   */
  public BizExceptionNotFound(String message) {
    super(message);
  }

  /**
   * Exception when a resource doesn't exist in biz.
   *
   * @param message the message of the excep.
   * @param cause   the exc upstream.
   */
  public BizExceptionNotFound(String message, Throwable cause) {
    super(message, cause);
  }
}
