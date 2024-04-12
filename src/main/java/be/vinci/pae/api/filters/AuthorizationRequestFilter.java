package be.vinci.pae.api.filters;

import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.services.UserDAO;
import be.vinci.pae.utils.Config;
import be.vinci.pae.utils.LoggerUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * This class represents a filter for authorizing access to resources.
 */
@Singleton
@Provider
@Authorize
public class AuthorizationRequestFilter implements ContainerRequestFilter {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final JWTVerifier jwtVerifier = JWT.require(this.jwtAlgorithm).withIssuer("auth0")
      .build();

  @Inject
  private UserDAO userDAO;
  @Context
  private ResourceInfo resourceInfo;

  /**
   * Filters the container request context to authorize requests based on JWT tokens and user
   * roles.
   *
   * @param requestContext The container request context to be filtered.
   * @throws IOException if an I/O error occurs.
   */
  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    LoggerUtil.logInfo("Starting : authorize");
    String[] rolesAllowed = null;
    Method method = resourceInfo.getResourceMethod();
    if (method != null) {
      Authorize rolesAnnotation = method.getAnnotation(Authorize.class);
      if (rolesAnnotation != null) {
        rolesAllowed = rolesAnnotation.roles();
      }
    }
    // Check if the user has required roles
    if (!userHasValidToken(requestContext)) {
      requestContext.abortWith(Response.status(Status.FORBIDDEN)
          .entity("You need to be connected to access to this resource").build());
      return;
    }
    UserDTO authenticatedUser = authenticateUser(requestContext);
    if (rolesAllowed == null) {
      requestContext.abortWith(Response.status(Status.FORBIDDEN)
          .entity("Error initialising Authorize").build());
    }
    if (!userHasRequiredRoles(authenticatedUser, rolesAllowed)) {
      requestContext.abortWith(Response.status(Status.FORBIDDEN)
          .entity("You are forbidden to access this resource").build());
      return;
    }
    requestContext.setProperty("user", authenticatedUser);
  }

  /**
   * Checks if the authenticated user has required roles.
   *
   * @param requestContext The container request context.
   * @return true if the user has required roles, false otherwise.
   */
  private boolean userHasValidToken(ContainerRequestContext requestContext) {
    String token = requestContext.getHeaderString("Authorization");

    if (token == null) {
      requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
          .entity("A token is needed to access this resource").build());
      return false;
    } else {
      try {
        jwtVerifier.verify(token.replace("Bearer ", ""));
        // You may add additional checks here if needed, such as token expiration or issuer validation
        LoggerUtil.logInfo("Token verified");
      } catch (Exception e) {
        throw new TokenDecodingException(e);
      }
      return true;
    }
  }


  /**
   * Authenticates the user based on JWT token.
   *
   * @param requestContext The container request context.
   * @return The authenticated user DTO.
   */
  private UserDTO authenticateUser(ContainerRequestContext requestContext) {
    String token = requestContext.getHeaderString("Authorization");
    DecodedJWT decodedToken = this.jwtVerifier.verify(token);
    int userId = decodedToken.getClaim("user").asInt();
    return userDAO.getOne(userId);
  }

  /**
   * Checks if the authenticated user has required roles.
   *
   * @param authenticatedUser The authenticated user.
   * @param rolesAllowed      The roles allowed for accessing the resource.
   * @return true if the user has required roles, false otherwise.
   */
  private boolean userHasRequiredRoles(UserDTO authenticatedUser, String[] rolesAllowed) {
    for (String allowedRole : rolesAllowed) {
      if (authenticatedUser.getRole().equals(allowedRole)) {
        return true;
      }
    }
    return false;
  }
}
