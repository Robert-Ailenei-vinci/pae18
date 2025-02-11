package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.controller.ContactUCC;
import be.vinci.pae.business.controller.EntrepriseUCC;
import be.vinci.pae.business.controller.UserUCC;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.exception.AuthorisationException;
import be.vinci.pae.exception.BadRequestException;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a resource for handling enterprise-related operations. It provides methods
 * to retrieve information about enterprises.
 */
@Singleton
@Path("/entreprise")
public class EntrepriseResource {

  private final ObjectMapper jsonMapper = new ObjectMapper();
  /**
   * The business controller for enterprise-related operations.
   */
  @Inject
  EntrepriseUCC myEntrepriseUCC;
  @Inject
  UserUCC myUserUCC;
  @Inject
  ContactUCC myContactUCC;

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
  @Authorize(roles = {"etudiant"})
  public List<EntrepriseDTO> getAll() {
    LoggerUtil.logInfo("Starting : enterprises/getAll");
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
  @Authorize(roles = {"etudiant", "professeur", "administratif"})
  public EntrepriseDTO getOne(@PathParam("id") int entrepriseId) {
    LoggerUtil.logInfo("Starting : enterprise/getOne");
    EntrepriseDTO toReturn = myEntrepriseUCC.getOne(entrepriseId);
    if (toReturn != null) {
      LoggerUtil.logInfo("GetOne successful");
    }
    return toReturn;
  }

  /**
   * Blacklists an enterprise. This method is accessed via HTTP POST request to the path
   * "/entreprise/blacklist". It returns the blacklisted enterprise in JSON format. Requires
   * authorization.
   *
   * @param json The JSON object containing the enterprise id and the reason for blacklisting
   * @return The {@link EntrepriseDTO} representing the blacklisted enterprise.
   */
  @POST
  @Path("blacklist")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"professeur"})
  public EntrepriseDTO blacklist(JsonNode json) {
    String reason = json.get("reason_blacklist").asText();
    int entrepriseId = json.get("id_entreprise").asInt();
    if (entrepriseId == 0 && reason.isEmpty()) {

      throw new BadRequestException("All fields required to blacklist an enterprise.");
    }

    EntrepriseDTO entreprise = myEntrepriseUCC.getOne(entrepriseId);

    if (entreprise == null) {
      throw new BadRequestException("Enterprise not found");
    }

    if (entreprise.isBlacklisted()) {
      throw new BadRequestException("Enterprise already blacklisted");
    }
    int version = json.get("version").asInt();
    EntrepriseDTO toReturn = myEntrepriseUCC.blacklist(entrepriseId, reason, version);
    if (toReturn != null) {
      LoggerUtil.logInfo("Blacklist successful");
    }
    return toReturn;
  }

  /**
   * Unblacklists an enterprise. This method is accessed via HTTP POST request to the path
   * "/entreprise/blacklist". It returns the unblacklisted enterprise in JSON format. Requires
   * authorization. This doesn't change the state of the contacts, it just allows to take a contact
   * with the beforehand blacklisted entreprise.
   *
   * @param json The JSON object containing the enterprise id.
   * @return The {@link EntrepriseDTO} representing the blacklisted enterprise.
   */
  @POST
  @Path("unblacklist")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"professeur"})
  public EntrepriseDTO unblacklist(JsonNode json) {
    int entrepriseId = json.get("id_entreprise").asInt();
    if (entrepriseId == 0) {

      throw new BadRequestException("All fields required to blacklist an enterprise.");
    }

    EntrepriseDTO entreprise = myEntrepriseUCC.getOne(entrepriseId);

    if (entreprise == null) {
      throw new BadRequestException("Enterprise not found");
    }

    if (!entreprise.isBlacklisted()) {
      throw new BadRequestException("Enterprise not blacklisted");
    }
    int version = json.get("version").asInt();

    EntrepriseDTO toReturn = myEntrepriseUCC.unblacklist(entrepriseId, version);
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
  @Authorize(roles = {"etudiant"})
  public EntrepriseDTO addEnterprise(@Context ContainerRequestContext requestContext,
      JsonNode json) throws BadRequestException, AuthorisationException {
    LoggerUtil.logInfo("Starting : enterprise/addOne");
    if (!json.hasNonNull("trade_name")
        || !json.hasNonNull("address")) {
      throw new BadRequestException("All fields required to create an enterprise.");
    }
    UserDTO user = (UserDTO) requestContext.getProperty("user"); // Conversion en int

    String tradeName = json.get("trade_name").asText();
    String designation = json.get("designation").asText();
    String address = json.get("address").asText();
    String phoneNum = json.get("phone_num").asText();
    String email = json.get("email").asText();

    EntrepriseDTO entrepriseDTO = myEntrepriseUCC.createOne(user, tradeName, designation, address,
        phoneNum, email);
    if (entrepriseDTO == null) {
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
  @Authorize(roles = {"professeur", "administratif"})
  public List<ContactDTO> getAllContactsByEntrepriseId(
      @PathParam("entrepriseId") int entrepriseId) {
    List<ContactDTO> toReturn = myEntrepriseUCC.getAllContactsByEntrepriseId(entrepriseId);
    if (toReturn != null) {
      LoggerUtil.logInfo("GetAllContactById successful");
    }
    return toReturn;
  }

  /**
   * Retrieves all enterprises for a given school year.
   *
   * @param idSchoolYear the school year id
   * @param orderBy      the field to order by
   * @return the list of enterprises for the school year
   */
  @GET
  @Path("getAllForSchoolYear/{idSchoolYear}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"professeur", "administratif"})
  public List<EntrepriseDTO> getAllForSchoolYear(@PathParam("idSchoolYear") int idSchoolYear,
      @QueryParam("orderBy") String orderBy) {
    // Validate orderBy parameter
    if (orderBy != null && !orderBy.isEmpty()) {
      String[] fields = orderBy.split(",");
      for (String field : fields) {
        if (!isValidField(field)) {
          throw new BadRequestException("Invalid field name in orderBy parameter: " + field);
        }
      }
    }
    List<EntrepriseDTO> toReturn;
    if (idSchoolYear == -1) {
      toReturn = myEntrepriseUCC.getAll();
    } else {
      toReturn = myEntrepriseUCC.getAllForSchoolYear(idSchoolYear, orderBy);
    }
    if (toReturn != null) {
      LoggerUtil.logInfo("GetAllForSchoolYear successful");
    }
    return toReturn;
  }

  private boolean isValidField(String field) {
    // List of valid field names in the pae.entreprises table
    List<String> validFields = Arrays.asList("id_entreprise", "email", "designation", "address",
        "phone_num", "blacklisted", "trade_name", "reason_blacklist");
    return validFields.contains(field);
  }

  /**
   * Count the number of stages for the entreprise.
   *
   * @param entrepriseId the entreprise id
   * @return the number of stages for the entreprise
   */
  @GET
  @Path("getStagesCountForCurrentYear/{entrepriseId}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"professeur", "administratif"})
  public ObjectNode getStagesCountForCurrentYear(@PathParam("entrepriseId") int entrepriseId) {
    ObjectNode toReturn = jsonMapper.createObjectNode();
    int nbStages = -1;
    nbStages = myEntrepriseUCC.getStagesCountForSchoolYear(entrepriseId);
    toReturn.put("nbStages", nbStages);
    if (nbStages != -1) {
      LoggerUtil.logInfo("GetStagesCountForCurrentYear successful");
    }
    return toReturn;
  }
}
