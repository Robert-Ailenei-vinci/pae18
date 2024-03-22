package be.vinci.pae.exception;

/**
 * Exception thrown when an optimistic locking conflict occurs.
 */
public class OptimisticLockException extends RuntimeException {

  /**
   * Constructs a new OptimisticLockException with the specified detail message.
   *
   * @param message the detail message. The detail message is saved for later retrieval by the
   *                Throwable.getMessage() method.
   */
  public OptimisticLockException(String message) {
    super(message);
  }
}
