package be.vinci.pae.exception;

public class BizExceptionNotFound extends RuntimeException {

  public BizExceptionNotFound(String message) {
    super(message);
  }

  public BizExceptionNotFound(String message, Throwable cause) {
    super(message, cause);
  }
}
