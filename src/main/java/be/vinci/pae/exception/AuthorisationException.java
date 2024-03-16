package be.vinci.pae.exception;

public class AuthorisationException extends RuntimeException {

  public AuthorisationException(String message) {
    super(message);
  }

  public AuthorisationException(String message, Throwable cause) {
    super(message, cause);
  }
}
