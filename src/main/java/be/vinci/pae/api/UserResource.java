package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.controller.UserUCC;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.utils.LoggerUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
   * Get one user based on id.
   *
   * @param userId id of user to get.
   * @return a dto of the user.
   */
  @GET
  @Path("getOne/{userId}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"professeur", "administratif"})
  public UserDTO getOne(@PathParam("userId") int userId) {
    LoggerUtil.logInfo("Starting : users/getOne");
    UserDTO toReturn = myUser.getOne(userId);
    if (toReturn != null) {
      LoggerUtil.logInfo("getOne " + userId + " successful");
    }
    return toReturn;
  }

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
  @Authorize(roles = {"professeur"})
  public List<UserDTO> getAll() {
    LoggerUtil.logInfo("Starting : users/getAll");
    List<UserDTO> toReturn = myUser.getAll();
    if (toReturn != null) {
      LoggerUtil.logInfo("Get All successful");
    }
    return toReturn;
  }

  /**
   * Adds a user. This method is annotated with {@link jakarta.ws.rs.POST} and
   * {@link jakarta.ws.rs.Consumes}, indicating that it handles HTTP POST requests and consumes JSON
   * data. Additionally, it is annotated with {@link be.vinci.pae.api.filters.Authorize}, indicating
   * that authorization is required to access this endpoint.
   *
   * @param json           The JSON representation of the user to be added.
   * @param requestContext The context of the HTTP request.
   * @return The added user.
   */
  @POST
  @Path("changeData")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"etudiant", "professeur", "administratif"})
  public ObjectNode changeData(JsonNode json, @Context ContainerRequestContext requestContext) {
    LoggerUtil.logInfo("Starting : users/changeData");

    String email = json.get("login").asText();

    JsonNode passwordNode = json.get("password");
    String password = passwordNode != null ? passwordNode.asText() : null;
    String lname = json.get("l_name").asText();
    String fname = json.get("f_name").asText();
    String phoneNum = json.get("phone_number").asText();
    int version = json.get("version").asInt();
    String token = requestContext.getHeaderString("Authorization");

    UserDTO publicUser = myUser.changeData(email, password, lname, fname, phoneNum, version);
    ObjectNode toReturn = jsonMapper.createObjectNode()
        .put("token", token)
        .put("id", publicUser.getId())
        .put("email", publicUser.getEmail())
        .put("firstName", publicUser.getFirstName())
        .put("lastName", publicUser.getLastName())
        .put("phoneNum", publicUser.getPhoneNum())
        .put("registrationDate", publicUser.getRegistrationDate())
        .put("role", publicUser.getRole())
        .put("schoolYear", publicUser.getSchoolYear().getYearFormat())
        .put("version", publicUser.getVersion());
    if (toReturn != null) {
      LoggerUtil.logInfo("Change Data successful");
    }
    return toReturn;
  }

  /**
   * Retrieves the number of students with internships. This method is annotated with {@link jakarta.ws.rs.GET}
   * and {@link jakarta.ws.rs.Path}, indicating that it handles HTTP GET requests and consumes JSON
   * data. Additionally, it is annotated with {@link be.vinci.pae.api.filters.Authorize}, indicating
   * that authorization is required to access this endpoint.
   *
   * @param yearID The identifier of the user to retrieve.
   * @return The user with the specified identifier.
   */
  @GET
  @Path("studWithStage/{filtre}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"professeur","administratif"})
  public int studsWithStage(@PathParam("filtre") int yearID) {
    LoggerUtil.logInfo("Starting : users/studWithStage");
    int toReturn = myUser.studsWithStage(yearID);
    if (toReturn != 0) {
      LoggerUtil.logInfo("Get studWithStage successful");
    }
    return toReturn;
  }

  /**
   * Retrieves the number of students without internships. This method is annotated with {@link jakarta.ws.rs.GET}
   * and {@link jakarta.ws.rs.Path}, indicating that it handles HTTP GET requests and consumes JSON
   * data. Additionally, it is annotated with {@link be.vinci.pae.api.filters.Authorize}, indicating
   * that authorization is required to access this endpoint.
   *
   * @param yearID The identifier of the user to retrieve.
   * @return The user with the specified identifier.
   */
  @GET
  @Path("studWithNoStage/{filtre}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"professeur","administratif"})
  public int studsWithNoStage(@PathParam("filtre") int yearID) {
    LoggerUtil.logInfo("Starting : users/studWithStage");
    int toReturn = myUser.studsWithNoStage(yearID);
    if (toReturn != 0) {
      LoggerUtil.logInfo("Get studWithStage successful");
    }
    return toReturn;
  }
}
