package be.vinci.pae.exception;

/**
 * Represents an exception that is thrown when an entreprise is not found.
 */
public class EntrepriseNotFoundException extends RuntimeException {

  public EntrepriseNotFoundException(String message) {
    super(message);
  }

  public EntrepriseNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
