package be.vinci.pae.api.filters;

import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.services.UserDAO;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

  private List<String> expectedRoles;

  /**
   * Factory method to create an instance of AuthorizationRequestFilter.
   *
   * @param roles The roles to authorize.
   * @return An instance of AuthorizationRequestFilter configured for the specified roles.
   */
  public static AuthorizationRequestFilter forRoles(String... roles) {
    AuthorizationRequestFilter filter = new AuthorizationRequestFilter();
    filter.expectedRoles = Arrays.asList(roles);
    return filter;
  }

  /**
   * Filters the container request context to authorize requests based on JWT tokens.
   *
   * @param requestContext The container request context to be filtered.
   * @throws IOException if an I/O error occurs.
   */
  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    String token = requestContext.getHeaderString("Authorization");
    if (token == null) {
      requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
          .entity("A token is needed to access this resource").build());
    } else {
      DecodedJWT decodedToken = null;
      try {
        decodedToken = this.jwtVerifier.verify(token);
        System.out.println("Token verified");
      } catch (Exception e) {
        throw new TokenDecodingException(e);
      }
      UserDTO authenticatedUser = userDAO.getOne(decodedToken.getClaim("user").asInt());
      if (authenticatedUser == null || !expectedRoles.contains(authenticatedUser.getRole())) {
        requestContext.abortWith(Response.status(Status.FORBIDDEN)
            .entity("You are forbidden to access this resource").build());
      }

      requestContext.setProperty("user", authenticatedUser);
    }
  }
}
