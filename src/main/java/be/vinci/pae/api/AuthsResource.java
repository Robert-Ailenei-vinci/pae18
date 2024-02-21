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
 * class permetant le login et register.
 */
@Singleton
@Path("/auths")
public class AuthsResource {

  /**
   * myUserDataService permet la conexion avec la db
   */
  @Inject
  private UserDataService myUserDataService;

  /**
   * Méthode pour gérer l'authentification des utilisateurs.
   *
   * @param json Objet JSON contenant les informations de connexion de l'utilisateur. Doit contenir
   *             les clés "login" et "password".
   * @return Un objet JSON représentant les informations publiques de l'utilisateur après connexion.
   * @throws WebApplicationException Si les informations de connexion sont manquantes ou
   *                                 incorrectes, une exception est levée avec le code d'erreur
   *                                 approprié.
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
   * @param user user qui s'inscrit
   * @return publicUser qui contient les info
   * @throws WebApplicationException Si les informations de connexion sont manquantes ou
   *                                 incorrectes, une exception est levée avec le code d'erreur
   *                                 approprié.
   */
  @POST
  @Path("register")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode register(User user) {
    // Get and check credentials
    if (user == null || user.getPassword() == null || user.getPassword().isBlank()
        || user.getLogin() == null || user.getLogin().isBlank()) {
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
