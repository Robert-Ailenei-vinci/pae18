package be.vinci.pae.exception;

public class StageNotFoundException extends RuntimeException {

  public StageNotFoundException(String message) {
    super(message);
  }

  public StageNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
