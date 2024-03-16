package be.vinci.pae.exception;

/**
 * Represents an exception that is thrown when a school year is not found.
 */
public class SchoolYearNotFoundException extends RuntimeException {

  public SchoolYearNotFoundException(String message) {
    super(message);
  }

  public SchoolYearNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
