package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.controller.EntrepriseUCC;
import be.vinci.pae.business.controller.SupervisorUCC;
import be.vinci.pae.business.controller.UserUCC;
import be.vinci.pae.business.domain.SupervisorDTO;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.exception.AuthorisationException;
import be.vinci.pae.utils.LoggerUtil;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

/**
 * Represents a RESTful API resource for managing supervisors.
 */
@Singleton
@Path("/supervisor")
public class SupervisorRessource {

  @Inject
  EntrepriseUCC myEntreprise;
  @Inject
  UserUCC myUserUCC;
  @Inject
  SupervisorUCC mySupervisorUCC;

  /**
   * Retrieves a list of all supervisors for one enterprise. This method is accessed via HTTP GET
   * request to the path "/supervisor/getAllForOneEnterprise". It returns the list of all
   * supervisors for the specified enterprise in JSON format. Requires authorization.
   *
   * @param entrepriseId the ID of the enterprise
   * @return A list of {@link SupervisorDTO} representing all supervisors for the specified entrpr
   * @throws BadRequestException if the enterprise ID is invalid or missing
   */
  @GET
  @Path("getAllForOneEnterprise")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"etudiant"})
  public List<SupervisorDTO> getAllForOneEnterprise(@QueryParam("entrepriseId") int entrepriseId)
      throws BadRequestException {
    if (entrepriseId <= 0) {
      throw new BadRequestException(
          "Enterprise ID is required and must be greater than zero");
    }

    List<SupervisorDTO> toReturn = mySupervisorUCC.getAll(entrepriseId);
    if (toReturn != null) {
      LoggerUtil.logInfo("GetAllForOneEnterprise successful");
    }
    return toReturn;
  }

  /**
   * Adds a new stage supervisor. This method is accessed via HTTP POST request to the path
   * "/supervisor/addSupervisor". It expects a JSON object containing the fields: first_name,
   * last_name, entreprise, phone_number, email. It returns the added supervisor in JSON format.
   * Requires authorization.
   *
   * @param requestContext The context of the HTTP request.
   * @param json           The JSON object containing supervisor details.
   * @return The {@link SupervisorDTO} representing the added supervisor.
   * @throws BadRequestException    If any required field is missing in the JSON object.
   * @throws AuthorisationException If the user is not recognized.
   */
  @POST
  @Path("addSupervisor")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"etudiant"})
  public SupervisorDTO addOneSupervisor(@Context ContainerRequestContext requestContext,
      JsonNode json) throws BadRequestException, AuthorisationException {
    LoggerUtil.logInfo("Starting: supervisor/addOne");
    LoggerUtil.logInfo(json.asText());
    if (!json.hasNonNull("lastName")
        || !json.hasNonNull("firstName")
        || !json.hasNonNull("entreprise")
        || !json.hasNonNull("phoneNum")) {
      throw new BadRequestException("All fields required to create a supervisor.");
    }

    UserDTO user = (UserDTO) requestContext.getProperty("user");
    int userId = user.getId();

    String lastName = json.get("lastName").asText();
    String firstName = json.get("firstName").asText();
    int entrepriseId = json.get("entreprise").asInt(); // si identifizant fourni
    String phoneNumber = json.get("phoneNum").asText();
    String email = json.hasNonNull("email") ? json.get("email").asText() : null; // email null

    // check
    UserDTO userDTO = myUserUCC.getOne(userId);
    if (userDTO == null) {
      throw new AuthorisationException("User not recognised");
    }

    // creation du superviseur
    SupervisorDTO supervisorDTO = mySupervisorUCC.createOne(userDTO, lastName, firstName,
        entrepriseId,
        phoneNumber, email);
    if (supervisorDTO == null) {
      throw new BadRequestException("Supervisor not created");
    }
    LoggerUtil.logInfo("addOneSupervisor successful");
    return supervisorDTO;
  }
}
