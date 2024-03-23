package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.controller.EntrepriseUCC;
import be.vinci.pae.business.controller.UserUCC;
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
  EntrepriseUCC myEntreprise;
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
    return myEntreprise.getAll();
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
  @Path("getOne")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public EntrepriseDTO getOne(int entrepriseId) {
    return myEntreprise.getOne(entrepriseId);
  }

  @POST
  @Path("addOne")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public EntrepriseDTO addEnterprise(@Context ContainerRequestContext requestContext,
      JsonNode json) {
    if (!json.hasNonNull("trade_name")
        || !json.hasNonNull("address")) {
      throw new BadRequestException("All fields required to create an enterprise.");
    }
    UserDTO user = (UserDTO) requestContext.getProperty("user"); // Conversion en int
    int userId = user.getId();
    String tradeName = json.get("trade_name").asText();
    String designation = json.get("designation").asText();
    String address = json.get("address").asText();
    String phoneNum = json.get("phone_num").asText();
    String email = json.get("email").asText();
    LoggerUtil.logInfo(tradeName + designation + address + phoneNum + email + user);
    // Try to get user, entreprise, and school year
    UserDTO userDTO = myUserUCC.getOne(userId);
    if (userDTO == null) {
      throw new AuthorisationException("User not recognised");
    }

    EntrepriseDTO entrepriseDTO = myEntreprise.createOne(userDTO, tradeName, designation, address,
        phoneNum, email);
    if (entrepriseDTO == null) {
      throw new BadRequestException("Contact not created");
    }

    return entrepriseDTO;
  }
}
