package be.vinci.pae.utils;

import be.vinci.pae.exception.FatalError;
import be.vinci.pae.exception.UserNotFoundException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.sql.SQLException;

@Provider
public class WebExceptionMapper implements ExceptionMapper<Throwable> {

  @Override
  public Response toResponse(Throwable exception) {
    exception.printStackTrace();
    if (exception instanceof WebApplicationException) {
      return Response.status(((WebApplicationException) exception).getResponse().getStatus())
          .entity(exception.getMessage())
          .build();
    }

    if (exception instanceof NotFoundException) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity(exception.getMessage())
          .build();
    }

    if (exception instanceof SQLException) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(exception.getMessage())
          .build();
    }

    if (exception instanceof UserNotFoundException) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity(exception.getMessage())
          .build();
    }

    if (exception instanceof FatalError) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(exception.getMessage())
          .build();
    }

    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(exception.getMessage())
        .build();
  }
}