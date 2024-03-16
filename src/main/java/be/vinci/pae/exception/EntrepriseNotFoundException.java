package be.vinci.pae.exception;

public class EntrepriseNotFoundException extends RuntimeException {

  public EntrepriseNotFoundException(String message) {
    super(message);
  }

  public EntrepriseNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
