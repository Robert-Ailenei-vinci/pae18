package be.vinci.pae.exception;

public class SchoolYearNotFoundException extends RuntimeException {

  public SchoolYearNotFoundException(String message) {
    super(message);
  }

  public SchoolYearNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
