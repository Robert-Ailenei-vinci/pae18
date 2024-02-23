package be.vinci.pae.api;

import be.vinci.pae.controller.UserUCC;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();
  /**
   * This service provides methods for user-related operations.
   */
  @Inject
  private UserUCC myUser;

  /**
   * Method for handling user authentication.
   *
   * @param json JSON object containing the user's login information. It must contain keys "login"
   *             and "password".
   * @return A JSON object representing the user's public information after successful auth
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
    UserDTO publicUser = myUser.login(login, password);
    if (publicUser == null) {
      throw new WebApplicationException("Login or password incorrect",
          Response.Status.UNAUTHORIZED);
    }
    String token;
    try {
      token = JWT.create().withIssuer("auth0")
          .withClaim("user", publicUser.getId()).sign(this.jwtAlgorithm);
      ObjectNode toReturn = jsonMapper.createObjectNode()
          .put("token", token)
          .put("id", publicUser.getId())
          .put("email", publicUser.getEmail());
      return toReturn;

    } catch (Exception e) {
      System.out.println("Unable to create token");
      return null;
    }

  }

  /*
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
    ObjectNode publicUser = myUser.register(user);
    if (publicUser == null) {
      throw new WebApplicationException("this resource already exists", Response.Status.CONFLICT);
    }
    return publicUser;

  }
*/
}
