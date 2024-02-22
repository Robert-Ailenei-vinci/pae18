package be.vinci.pae.utils;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * This class maps exceptions to appropriate HTTP responses for web applications.
 */
@Provider
public class WebExceptionMapper implements ExceptionMapper<Throwable> {

  /**
   * Maps the given exception to an HTTP response.
   *
   * @param exception The exception to map.
   * @return The HTTP response representing the exception.
   */
  @Override
  public Response toResponse(Throwable exception) {
    exception.printStackTrace();
    if (exception instanceof WebApplicationException) {
      return Response.status(((WebApplicationException) exception).getResponse().getStatus())
          .entity(exception.getMessage())
          .build();
    }
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(exception.getMessage())
        .build();
  }
}
