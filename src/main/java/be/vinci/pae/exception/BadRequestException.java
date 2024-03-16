package be.vinci.pae.exception;

/**
 * Represents an exception that is thrown when a request is not valid.
 */
public class BadRequestException extends RuntimeException {

  public BadRequestException(String message) {
    super(message);
  }
}