package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.controller.EntrepriseUCC;
import be.vinci.pae.business.controller.SupervisorUCC;
import be.vinci.pae.business.controller.UserUCC;
import be.vinci.pae.business.domain.SupervisorDTO;
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
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

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
   * Retrieves a list of all supervisors. This method is accessed via HTTP GET request to the path
   * "/supervisor/getAll". It returns the list of all supervisors in JSON format. Requires
   * authorization.
   *
   * @return A list of {@link SupervisorDTO} representing all enterprises.
   */
  @GET
  @Path("getAll")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<SupervisorDTO> getAll() {
    List<SupervisorDTO> toReturn = mySupervisorUCC.getAll();
    if (toReturn != null) {
      LoggerUtil.logInfo("GetAll successful");
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
   * @param json           The JSON object containing enterprise details.
   * @return The {@link SupervisorDTO} representing the added supervisor.
   * @throws be.vinci.pae.exception.BadRequestException If any required field is missing in the JSON
   *                                                    object.
   * @throws AuthorisationException                     If the user is not recognized.
   */
  @POST
  @Path("addSupervisor")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize
  public SupervisorDTO addOneSupervisor(@Context ContainerRequestContext requestContext,
      JsonNode json) throws BadRequestException, AuthorisationException {

    return null;
  }
}

