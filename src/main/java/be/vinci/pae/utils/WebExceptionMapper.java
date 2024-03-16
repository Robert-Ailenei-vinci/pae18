package be.vinci.pae.utils;

import be.vinci.pae.exception.AuthorisationException;
import be.vinci.pae.exception.BadRequestException;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.exception.EntrepriseNotFoundException;
import be.vinci.pae.exception.FatalError;
import be.vinci.pae.exception.SchoolYearNotFoundException;
import be.vinci.pae.exception.StageNotFoundException;
import be.vinci.pae.exception.SupervisorNotFoundException;
import be.vinci.pae.exception.UserNotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
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
    if (exception instanceof StageNotFoundException) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity(exception.getMessage())
          .build();
    }

    if (exception instanceof SupervisorNotFoundException) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity(exception.getMessage())
          .build();
    }
    if (exception instanceof SchoolYearNotFoundException) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity(exception.getMessage())
          .build();
    }

    if (exception instanceof EntrepriseNotFoundException) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity(exception.getMessage())
          .build();
    }
    if (exception instanceof BizException) {
      return Response.status(Response.Status.CONFLICT)
          .entity(exception.getMessage())
          .build();
    }
    if (exception instanceof AuthorisationException) {
      return Response.status(Status.UNAUTHORIZED)
          .entity(exception.getMessage())
          .build();
    }
    if (exception instanceof BadRequestException) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(exception.getMessage())
          .build();
    }

    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(exception.getMessage())
        .build();
  }
}