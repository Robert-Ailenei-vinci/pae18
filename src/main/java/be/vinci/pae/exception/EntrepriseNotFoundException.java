package be.vinci.pae.exception;

/**
 * Represents an exception that is thrown when an entreprise is not found.
 */
public class EntrepriseNotFoundException extends RuntimeException {

  /**
   * Constructs a new EntrepriseNotFoundException with the specified detail message.
   *
   * @param message the detail message. The detail message is saved for later retrieval by the
   *                Throwable.getMessage() method.
   */
  public EntrepriseNotFoundException(String message) {
    super(message);
  }

  /**
   * Constructs a new EntrepriseNotFoundException with the specified detail message and cause.
   *
   * @param message the detail message. The detail message is saved for later retrieval by the
   *                Throwable.getMessage() method.
   * @param cause   the cause (which is saved for later retrieval by the Throwable.getCause()
   *                method). A null value is permitted, and indicates that the cause is nonexistent
   *                or unknown.
   */
  public EntrepriseNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}