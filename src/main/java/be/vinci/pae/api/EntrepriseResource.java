package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.controller.EntrepriseUCC;
import be.vinci.pae.business.controller.UserUCC;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.exception.AuthorisationException;
import be.vinci.pae.exception.BadRequestException;
import be.vinci.pae.utils.LoggerUtil;
import com.fasterxml.jackson.databind.JsonNode;
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
 * This class represents a resource for handling enterprise-related operations. It provides methods
 * to retrieve information about enterprises.
 */
@Singleton
@Path("/entreprise")
public class EntrepriseResource {

  /**
   * The business controller for enterprise-related operations.
   */
  @Inject
  EntrepriseUCC myEntrepriseUCC;
  @Inject
  UserUCC myUserUCC;

  /**
   * Retrieves a list of all enterprises. This method is accessed via HTTP GET request to the path
   * "/entreprise/getAll". It returns the list of all enterprises in JSON format. Requires
   * authorization.
   *
   * @return A list of {@link EntrepriseDTO} representing all enterprises.
   */
  @GET
  @Path("getAll")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<EntrepriseDTO> getAll() {
    List<EntrepriseDTO> toReturn = myEntrepriseUCC.getAll();

    if (toReturn != null) {
      LoggerUtil.logInfo("GetAll successful");
    }
    return toReturn;
  }

  /**
   * Retrieves an enterprise by its id. This method is accessed via HTTP GET request to the path
   * "/entreprise/getOne". It returns the enterprise with the provided id in JSON format. Requires
   * authorization.
   *
   * @param entrepriseId The id of the enterprise to retrieve.
   * @return The {@link EntrepriseDTO} representing the enterprise.
   */
  @GET
  @Path("getOne/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public EntrepriseDTO getOne(@PathParam("id") int entrepriseId) {
    EntrepriseDTO toReturn = myEntrepriseUCC.getOne(entrepriseId);
    if (toReturn != null) {
      LoggerUtil.logInfo("GetOne successful");
    }
    return toReturn;
  }

  /**
   * Blacklists an enterprise. This method is accessed via HTTP POST request to the path
   * "/entreprise/blacklist/{id}". It returns the blacklisted enterprise in JSON format. Requires
   * authorization.
   *
   * @return The {@link EntrepriseDTO} representing the blacklisted enterprise.
   */
  @POST
  @Path("blacklist")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)

  public EntrepriseDTO blacklist(JsonNode json,
      @Context ContainerRequestContext requestContext) {
    String reason = json.get("reason").asText();
    int entrepriseId = json.get("id").asInt();
    String token = requestContext.getHeaderString("Authorization");

    if (entrepriseId == 0 && reason.isEmpty()) {
      LoggerUtil.logError("All fields required to blacklist an enterprise.",
          new BadRequestException(""));
      throw new BadRequestException("All fields required to blacklist an enterprise.");
    }

    EntrepriseDTO toReturn = myEntrepriseUCC.blacklist(entrepriseId, reason);
    if (toReturn != null) {
      LoggerUtil.logInfo("Blacklist successful");
    }
    return toReturn;
  }


  /**
   * Adds a new enterprise. This method is accessed via HTTP POST request to the path
   * "/entreprise/addOne". It expects a JSON object containing the fields: trade_name, designation,
   * address, phone_num, email. It returns the added enterprise in JSON format. Requires
   * authorization.
   *
   * @param requestContext The context of the HTTP request.
   * @param json           The JSON object containing enterprise details.
   * @return The {@link EntrepriseDTO} representing the added enterprise.
   * @throws BadRequestException    If any required field is missing in the JSON object.
   * @throws AuthorisationException If the user is not recognized.
   */
  @POST
  @Path("addOne")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public EntrepriseDTO addEnterprise(@Context ContainerRequestContext requestContext,
      JsonNode json) throws BadRequestException, AuthorisationException {
    if (!json.hasNonNull("trade_name")
        || !json.hasNonNull("address")) {
      LoggerUtil.logError("All fields required to create an enterprise.",
          new BadRequestException(""));
      throw new BadRequestException("All fields required to create an enterprise.");
    }
    UserDTO user = (UserDTO) requestContext.getProperty("user"); // Conversion en int
    int userId = user.getId();
    String tradeName = json.get("trade_name").asText();
    String designation = json.get("designation").asText();
    String address = json.get("address").asText();
    String phoneNum = json.get("phone_num").asText();
    String email = json.get("email").asText();

    // Try to get user, enterprise, and school year
    UserDTO userDTO = myUserUCC.getOne(userId);
    if (userDTO == null) {
      LoggerUtil.logError("User not recognised", new AuthorisationException(""));
      throw new AuthorisationException("User not recognised");
    }

    EntrepriseDTO entrepriseDTO = myEntrepriseUCC.createOne(userDTO, tradeName, designation,
        address,
        phoneNum, email);
    if (entrepriseDTO == null) {
      LoggerUtil.logError("Contact not created", new BadRequestException(""));
      throw new BadRequestException("Contact not created");
    }
    LoggerUtil.logInfo("addOne successful");
    return entrepriseDTO;
  }

  /**
   * Retrieves all contacts associated with one entreprise.
   *
   * @param entrepriseId the entreprise id
   * @return the list of contacts associated with the entreprise
   */
  @GET
  @Path("entrepriseDetailsAllContacts/{entrepriseId}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<ContactDTO> getAllContactsByEntrepriseId(
      @PathParam("entrepriseId") int entrepriseId) {
    List<ContactDTO> toReturn = myEntrepriseUCC.getAllContactsByEntrepriseId(entrepriseId);
    if (toReturn != null) {
      LoggerUtil.logInfo("GetAllContactById successful");
    }
    return toReturn;
  }
}
