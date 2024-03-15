package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.controller.UserUCC;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
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
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.util.List;


/**
 * This class represents a resource for handling user-related operations. The base path for this
 * resource is "/users".
 */
@Singleton
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

  private final ObjectMapper jsonMapper = new ObjectMapper();
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
  @Path("getAll")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<UserDTO> getAll() {
    System.out.println("getAll");
    return myUser.getAll();
  }

  /**
   * Adds a user. This method is annotated with {@link jakarta.ws.rs.POST} and
   * {@link jakarta.ws.rs.Consumes}, indicating that it handles HTTP POST requests and consumes JSON
   * data. Additionally, it is annotated with {@link be.vinci.pae.api.filters.Authorize},
   * indicating that authorization is required to access this endpoint.
   *
   * @param json The JSON representation of the user to be added.
   * @param requestContext The context of the HTTP request.
   * @return The added user.
   */
  @POST
  @Path("changeData")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ObjectNode changeData(JsonNode json, @Context ContainerRequestContext requestContext) {

    String email = json.get("login").asText();
    System.out.println(email);

    JsonNode passwordNode = json.get("password");
    String password = passwordNode != null ? passwordNode.asText() : null;

    System.out.println(password);
    String lname = json.get("l_name").asText();
    System.out.println(lname);
    String fname = json.get("f_name").asText();
    System.out.println(fname);
    String phoneNum = json.get("phone_number").asText();
    System.out.println(phoneNum);
    String token = requestContext.getHeaderString("Authorization");
    System.out.println(token);

    UserDTO publicUser = myUser.changeData(email, password, lname, fname, phoneNum);

    ObjectNode toReturn = jsonMapper.createObjectNode()
        .put("token", token)
        .put("id", publicUser.getId())
        .put("password", publicUser.getPassword())
        .put("email", publicUser.getEmail())
        .put("firstName", publicUser.getFirstName())
        .put("lastName", publicUser.getLastName())
        .put("phoneNum", publicUser.getPhoneNum())
        .put("registrationDate", publicUser.getRegistrationDate())
        .put("role", publicUser.getRole());

    return toReturn;
  }
}
