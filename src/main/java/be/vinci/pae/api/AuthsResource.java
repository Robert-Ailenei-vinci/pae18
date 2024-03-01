package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.controller.UserUCC;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
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
  public UserDTO login(JsonNode json) {
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
          Response.Status.BAD_REQUEST);
    }
    String token;
    try {
      token = JWT.create().withIssuer("auth0")
          .withClaim("user", publicUser.getId()).sign(this.jwtAlgorithm);
      System.out.println("Token " + token);
    } catch (Exception e) {
      System.out.println("Unable to create token");
      return null;
    }
    return publicUser;
  }

  /**
   * Get the connected User for refresh.
   *
   * @param requestContext requestContext
   * @return the connected user
   */
  @GET
  @Path("user")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public UserDTO getUser(@Context ContainerRequestContext requestContext) {
    UserDTO user = (UserDTO) requestContext.getProperty("user");

    if (user == null) {
      throw new WebApplicationException("User not found", Response.Status.UNAUTHORIZED);
    }
    return user;
  }

  @POST
  @Path("register")
  public boolean register(JsonNode json) {
    if (!json.hasNonNull("email") || !json.hasNonNull("password") || !json.hasNonNull("lname")
        || !json.hasNonNull("fname") || !json.hasNonNull("phoneNum") || !json.hasNonNull("role")) {
      throw new WebApplicationException("All fields are required", Response.Status.BAD_REQUEST);
    }
    String email = json.get("email").asText();
    String password = json.get("password").asText();
    String lname = json.get("lname").asText();
    String fname = json.get("fname").asText();
    String phoneNum = json.get("phoneNum").asText();
    String role = json.get("role").asText();
    return myUser.register(email, password, lname, fname, phoneNum, role);
  }
}
