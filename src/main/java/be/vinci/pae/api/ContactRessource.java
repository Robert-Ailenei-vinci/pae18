package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.controller.ContactUCC;
import be.vinci.pae.business.controller.EntrepriseUCC;
import be.vinci.pae.business.controller.SchoolYearUCC;
import be.vinci.pae.business.controller.StageUCC;
import be.vinci.pae.business.controller.SupervisorUCC;
import be.vinci.pae.business.controller.UserUCC;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.SchoolYearDTO;
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
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

/**
 * Represents a RESTful API resource for managing contacts.
 */
@Singleton
@Path("/contacts")
public class ContactRessource {

  @Inject
  private EntrepriseUCC myEntrepriseUCC;
  @Inject
  private ContactUCC myContactUCC;
  @Inject
  private UserUCC myUserUCC;
  @Inject
  private SchoolYearUCC mySchoolYearUCC;
  @Inject
  private StageUCC myStageUCC;
  @Inject
  private SupervisorUCC mySupervisorUCC;

  /**
   * Adds a contact.
   *
   * @param requestContext the auth user.
   * @param json           The JSON representation of the contact to be added.
   * @return The added contact.
   * @throws BadRequestException If user, entreprise, or school year is missing or not recognized.
   */
  @POST
  @Path("add")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"etudiant"})
  public ContactDTO addContact(@Context ContainerRequestContext requestContext, JsonNode json) {
    LoggerUtil.logInfo("Starting : contacts/add");
    if (!json.hasNonNull("entreprise")) {
      throw new BadRequestException("User, entreprise, and school year required");
    }
    UserDTO user = (UserDTO) requestContext.getProperty("user"); // Conversion en int
    int userId = user.getId();
    int entrepriseId = json.get("entreprise").asInt();
    int schoolYearId = user.getSchooYearId();

    // Try to get user, entreprise, and school year
    UserDTO userDTO = myUserUCC.getOne(userId);
    EntrepriseDTO entrepriseDTO = myEntrepriseUCC.getOne(entrepriseId);
    SchoolYearDTO schoolYearDTO = mySchoolYearUCC.getOne(schoolYearId);
    if (userDTO == null) {
      throw new AuthorisationException("User not recognised");
    }
    if (entrepriseDTO == null) {
      throw new AuthorisationException("Entreprise not recognised");
    }
    if (schoolYearDTO == null) {
      throw new AuthorisationException("Schoolyear not recognised");
    }

    ContactDTO toReturn = myContactUCC.createOne(userDTO, entrepriseDTO, schoolYearDTO);
    if (toReturn == null) {
      throw new BadRequestException("Contact not created");
    }
    if (toReturn != null) {
      LoggerUtil.logInfo("Add successful");
    }
    return toReturn;
  }

  /**
   * Retrieves all  contacts associated with one user.
   *
   * @param requestContext the request context
   * @return the list of contacts associated with the user
   */
  @GET
  @Path("allContactsByUserId")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"etudiant", "professeur", "administratif"})
  public List<ContactDTO> getAllContactsByUserId(@Context ContainerRequestContext requestContext) {
    LoggerUtil.logInfo("Starting : contact/getAllContactsByUserId");
    UserDTO authentifiedUser = (UserDTO) requestContext.getProperty("user");
    int userId = authentifiedUser.getId();
    List<ContactDTO> toReturn = myContactUCC.getAllContactsByUserId(userId);
    if (toReturn != null) {
      LoggerUtil.logInfo("GetAllContactById successful");
    }
    return toReturn;
  }

  /**
   * Retrieves all  contacts associated with one user.
   *
   * @param userId id of the user.
   * @return the list of contacts associated with the user.
   */
  @GET
  @Path("allContactsByUserId/{userId}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"etudiant", "professeur", "administratif"})
  public List<ContactDTO> getAllContactsByUserId(@PathParam("userId") int userId) {
    LoggerUtil.logInfo("Starting : contact/getAllContactsByUserId");

    List<ContactDTO> toReturn = myContactUCC.getAllContactsByUserId(userId);
    if (toReturn != null) {
      LoggerUtil.logInfo("GetAllContactById successful");
    }
    return toReturn;
  }

  /**
   * Retrieves one contacts by id.
   *
   * @param contactId id of the user.
   * @return a contact.
   */
  @GET
  @Path("getOneContactById")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"etudiant", "professeur", "administratif"})
  public ContactDTO getOneContactById(@QueryParam("contactId") int contactId) {
    LoggerUtil.logInfo("Starting : contact/getOneContactById");

    ContactDTO toReturn = myContactUCC.getOneContactById(contactId);
    if (toReturn != null) {
      LoggerUtil.logInfo("getOneContactById successful");
    }
    return toReturn;
  }

  /**
   * path to meet a contact.
   *
   * @param requestContext the auth user.
   * @param json           the id and meeting type of the contact.
   * @return the contact updated.
   */
  @PUT
  @Path("meet")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"etudiant"})
  public ContactDTO meetContact(@Context ContainerRequestContext requestContext, JsonNode json) {
    LoggerUtil.logInfo("Starting : contacts/meet");
    if (!json.hasNonNull("id_contact") && json.hasNonNull("meetingType")) {
      throw new BadRequestException("contact id and meeting type required");
    }
    int contactId = json.get("id_contact").asInt();
    String meetingType = json.get("meetingType").asText();
    int version = json.get("version").asInt();
    UserDTO user = (UserDTO) requestContext.getProperty("user"); // Conversion en int
    int userId = user.getId();
    ContactDTO toReturn = myContactUCC.meetContact(contactId, meetingType, userId, version);
    if (toReturn != null) {
      LoggerUtil.logInfo("Meet successful");
    }
    return toReturn;
  }

  /**
   * path to stop following a contact.
   *
   * @param requestContext the auth user.
   * @param json           the id of the contact.
   * @return the contact updated.
   */
  @PUT
  @Path("stopFollow")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"etudiant"})
  public ContactDTO stopFollowContact(@Context ContainerRequestContext requestContext,
      JsonNode json) {
    LoggerUtil.logInfo("Starting : contacts/stopFollow");
    if (!json.hasNonNull("id_contact")) {
      throw new BadRequestException("contact id required");
    }
    int contactId = json.get("id_contact").asInt();
    UserDTO user = (UserDTO) requestContext.getProperty("user"); // Conversion en int
    int version = json.get("version").asInt();
    int userId = user.getId();
    ContactDTO toReturn = myContactUCC.stopFollowContact(contactId, userId, version);
    if (toReturn != null) {
      LoggerUtil.logInfo("Stop Follow successful");
    }
    return toReturn;
  }


  /**
   * path to refuse a contact.
   *
   * @param requestContext the auth user.
   * @param json           the id and reason pf refusal of the contact.
   * @return the contact updated.
   */
  @PUT
  @Path("refused")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"etudiant"})
  public ContactDTO refusedContact(@Context ContainerRequestContext requestContext, JsonNode json) {
    LoggerUtil.logInfo("Starting : contacts/refused");
    if (!json.hasNonNull("id_contact") && json.hasNonNull("refusalReason")) {
      throw new BadRequestException("contact id and refusal reason required");
    }
    int contactId = json.get("id_contact").asInt();
    String refusalReason = json.get("refusalReason").asText();
    int version = json.get("version").asInt();
    UserDTO user = (UserDTO) requestContext.getProperty("user"); // Conversion en int
    int userId = user.getId();
    ContactDTO toReturn = myContactUCC.refusedContact(contactId, refusalReason, userId, version);
    if (toReturn != null) {
      LoggerUtil.logInfo("Refused successful");
    }
    return toReturn;
  }

  /**
   * path to accept a contact thus creating a stage.
   *
   * @param requestContext the auth user.
   * @param json           the id of the contact.
   * @return the created stage.
   */
  @PUT
  @Path("accept")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"etudiant"})
  public ContactDTO acceptContact(@Context ContainerRequestContext requestContext, JsonNode json) {
    if (!json.hasNonNull("id_contact")) {
      throw new BadRequestException("contact id required");
    }
    int contactId = json.get("id_contact").asInt();
    int contactVersion = json.get("version").asInt();
    int supervisorId = json.get("id_supervisor").asInt();
    String signatureDate = json.get("signatureDate").asText();
    String internshipProject = json.get("intershipProject").asText();
    if (signatureDate.isBlank() || signatureDate.isEmpty()) {
      throw new BadRequestException("Signature date is mandatory");
    }

    UserDTO user = (UserDTO) requestContext.getProperty("user"); // Conversion en int
    int userId = user.getId();
    ContactDTO acceptedContact = myContactUCC.acceptContact(contactId, userId, contactVersion,
        supervisorId, signatureDate, internshipProject);

    if (acceptedContact != null) {
      LoggerUtil.logInfo("Stage accepted successfully");
    }
    return acceptedContact;
  }
}
