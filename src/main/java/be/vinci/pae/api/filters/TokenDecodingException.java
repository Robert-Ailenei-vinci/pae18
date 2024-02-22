package be.vinci.pae.api.filters;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

/**
 * This exception is thrown when an error occurs during the decoding of an authorization token.
 * Instances of this exception typically indicate issues such as invalid or expired tokens,
 * or other problems encountered during the decoding process.
 */
public class TokenDecodingException extends WebApplicationException {
    /**
     * Constructs a new TokenDecodingException with no detail message.
     */
    public TokenDecodingException() {
        super(Response.Status.UNAUTHORIZED);
    }

    /**
     * Constructs a new TokenDecodingException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by
     *                the {@link #getMessage()} method)
     */
    public TokenDecodingException(String message) {
        super(message, Response.Status.UNAUTHORIZED);
    }

    /**
     * Constructs a new TokenDecodingException with the specified cause and a detail message
     * that is the cause's string representation.
     *
     * @param cause The cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public TokenDecodingException(Throwable cause) {
        super(cause.getMessage(), Response.Status.UNAUTHORIZED);
    }
}