package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.controller.UserUCC;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;


/**
 * This class represents a resource for handling user-related operations. The base path for this
 * resource is "/users".
 */
@Singleton
@Path("/users")
public class UserResource {

  /**
   * This service provides methods for user-related operations.
   */
  @Inject
  private UserUCC myUser;

  /**
   * Retrieves a list of all users. This method is annotated with {@link jakarta.ws.rs.GET} and
   * {@link jakarta.ws.rs.Produces}, indicating that it handles HTTP GET requests and produces JSON
   * responses. Additionally, it is annotated with {@link be.vinci.pae.api.filters.Authorize},
   * indicating that authorization is required to access this endpoint.
   *
   * @return list of {@link User} objects representing all users in the system.
   */

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<UserDTO> getAll() {
    System.out.println("getAll");
    return myUser.getAll();
  }

}
