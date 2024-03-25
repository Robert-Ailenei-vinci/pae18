package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.controller.UserUCC;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.exception.AuthorisationException;
import be.vinci.pae.exception.BadRequestException;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import java.time.Instant;
import java.util.Date;

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
  private UserUCC userUCC;

  @Inject
  private DomainFactory myDomainFactory;

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
      throw new BadRequestException("Login and password required");
    }
    String login = json.get("login").asText();
    String password = json.get("password").asText();

    // Try to login
    UserDTO publicUser = userUCC.login(login, password);
    if (publicUser == null) {
      throw new AuthorisationException("Login failed");
    }
    String token;
    Instant now = Instant.now();
    Instant expirationTime = now.plusSeconds(3600); // 1 heure à partir de maintenant

    try {
      token = JWT.create()
          .withIssuer("auth0")
          .withClaim("user", publicUser.getId())
          .withExpiresAt(Date.from(expirationTime))
          .sign(this.jwtAlgorithm);
      System.out.println("Token " + token);
      ObjectNode toReturn = jsonMapper.createObjectNode()
          .put("token", token)
          .put("id", publicUser.getId())
          .put("email", publicUser.getEmail())
          .put("role", publicUser.getRole())
          .put("firstName", publicUser.getFirstName())
          .put("lastName", publicUser.getLastName())
          .put("phoneNum", publicUser.getPhoneNum())
          .put("registrationDate", publicUser.getRegistrationDate())
          .put("schoolYear", publicUser.getSchoolYear().getYearFormat())
          .put("version", publicUser.getVersion());
      return toReturn;

    } catch (Exception e) {
      System.out.println("Unable to create token");
      return null;
    }
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
  public ObjectNode getUser(@Context ContainerRequestContext requestContext) {
    System.out.println("refresh");
    UserDTO user = (UserDTO) requestContext.getProperty("user");

    if (user == null) {
      throw new AuthorisationException("User not recognised");
    }
    String token;
    Instant now = Instant.now();
    Instant expirationTime = now.plusSeconds(3600); // 1 heure à partir de maintenant

    try {
      token = JWT.create()
          .withIssuer("auth0")
          .withClaim("user", user.getId())
          .withExpiresAt(Date.from(expirationTime))
          .sign(this.jwtAlgorithm);
      System.out.println("Token " + token);
      ObjectNode toReturn = jsonMapper.createObjectNode()
          .put("token", token)
          .put("id", user.getId())
          .put("email", user.getEmail())
          .put("role", user.getRole())
          .put("firstName", user.getFirstName())
          .put("lastName", user.getLastName())
          .put("phoneNum", user.getPhoneNum())
          .put("registrationDate", user.getRegistrationDate())
          .put("schoolYear", user.getSchoolYear().getYearFormat())
          .put("version", user.getVersion());
      return toReturn;

    } catch (Exception e) {
      System.out.println("Unable to create token");
      return null;
    }
  }

  /**
   * Register a new user.
   *
   * @param json JSON object containing the user's registration information. It must contain keys
   *             "login", "password", "lname", "fname" and "phoneNum".
   * @return true if the user is registered, false if not.
   * @throws WebApplicationException If any of the required fields are missing, a
   *                                 WebApplicationException with the appropriate error code is
   *                                 thrown.
   */
  @POST
  @Path("register")
  @Consumes(MediaType.APPLICATION_JSON)
  public boolean register(JsonNode json) {
    String email = json.get("login").asText();
    String password = json.get("password").asText();
    String lname = json.get("l_name").asText();
    String fname = json.get("f_name").asText();
    String phoneNum = json.get("phone_number").asText();
    String role = json.get("role").asText();
    int version = json.get("version").asInt();
    UserDTO user = myDomainFactory.getUser();
    user.setEmail(email);
    user.setPassword(password);
    user.setFirstName(fname);
    user.setLastName(lname);
    user.setPhoneNum(phoneNum);
    user.setRole(role);
    user.setVersion(version);

    return userUCC.register(user);
  }
}