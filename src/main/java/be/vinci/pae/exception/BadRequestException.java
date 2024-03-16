package be.vinci.pae.exception;

/**
 * Represents an exception that is thrown when a request is not valid.
 */
public class BadRequestException extends RuntimeException {


  /**
   * Constructs a new BadRequestException with the specified detail message.
   *
   * @param message the detail message. The detail message is saved for later retrieval by the
   *                Throwable.getMessage() method.
   */
  public BadRequestException(String message) {
    super(message);
  }
}