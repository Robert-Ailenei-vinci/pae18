package be.vinci.pae.exception;

/**
 * Represents an exception that is thrown when a user is not authorised to perform an action.
 */
public class AuthorisationException extends RuntimeException {


  public AuthorisationException(String message) {
    //  super(message) : call the constructor of the parent class with the message as parameter
    super(message);
  }

  public AuthorisationException(String message, Throwable cause) {
    //  super(message, cause) : call the constructor of the parent class with the message and the cause as parameters
    super(message, cause);
  }
}
