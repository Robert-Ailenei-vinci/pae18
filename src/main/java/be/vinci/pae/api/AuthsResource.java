package be.vinci.pae.api;

import be.vinci.pae.domain.User;
import be.vinci.pae.services.UserDataService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * This class represents a resource for handling user authentication (login and registration). the
 * base path for this resource is "/auths".
 */
@Singleton
@Path("/auths")
public class AuthsResource {

  /**
   * This service provides methods for user-related operations.
   */
  @Inject
  private UserDataService myUserDataService;

  /**
   * Method for handling user authentication.
   *
   * @param json JSON object containing the user's login information. It must contain keys "login"
   *             and "password".
   * @return A JSON object representing the user's public information after successful
   * authentication.
   * @throws WebApplicationException If login information is missing or incorrect, a
   *                                 WebApplicationException with the appropriate error code is
   *                                 thrown.
   */
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode login(JsonNode json) {
    // Get and check credentials
    if (!json.hasNonNull("login") || !json.hasNonNull("password")) {
      throw new WebApplicationException("login or password required", Response.Status.BAD_REQUEST);
    }
    String login = json.get("login").asText();
    String password = json.get("password").asText();
    // Try to login
    ObjectNode publicUser = myUserDataService.login(login, password);
    if (publicUser == null) {
      throw new WebApplicationException("Login or password incorrect",
          Response.Status.UNAUTHORIZED);
    }
    return publicUser;
  }

  /**
   * Method for handling user registration.
   *
   * @param user The user object to be registered.
   * @return A JSON object containing the public information of the registered user.
   * @throws WebApplicationException If required user information is missing or incorrect, a
   *                                 WebApplicationException with the appropriate error code is
   *                                 thrown.
   */
  @POST
  @Path("register")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode register(User user) {
    // Get and check credentials
    if (user == null || user.getPassword() == null || user.getPassword().isBlank()
        || user.getEmail() == null || user.getEmail().isBlank()) {
      throw new WebApplicationException("login or password required", Response.Status.BAD_REQUEST);
    }
    // Try to login
    ObjectNode publicUser = myUserDataService.register(user);
    if (publicUser == null) {
      throw new WebApplicationException("this resource already exists", Response.Status.CONFLICT);
    }
    return publicUser;

  }

}
