package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.controller.ContactUCC;
import be.vinci.pae.business.controller.EntrepriseUCC;
import be.vinci.pae.business.controller.SchoolYearUCC;
import be.vinci.pae.business.controller.UserUCC;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.business.domain.UserDTO;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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

  /**
   * Adds a contact.
   *
   * @param json The JSON representation of the contact to be added.
   * @return The added contact.
   * @throws WebApplicationException If user, entreprise, or school year is missing or not
   *                                 recognized.
   */
  @POST
  @Path("add")
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize
  public ContactDTO addContact(JsonNode json) {
    if (!json.hasNonNull("user")
        || !json.hasNonNull("entreprise")
        || !json.hasNonNull("schoolYear")) {
      throw new WebApplicationException("user, entreprise or schoolYear required",
          Response.Status.BAD_REQUEST);
    }
    int userId = json.get("user").asInt();
    int entrepriseId = json.get("entreprise").asInt();
    int schoolYearId = json.get("schooYear").asInt();

    // Try to get user, entreprise, and school year
    UserDTO userDTO = myUserUCC.getOne(userId);
    EntrepriseDTO entrepriseDTO = myEntrepriseUCC.getOne(entrepriseId);
    SchoolYearDTO schoolYearDTO = mySchoolYearUCC.getOne(schoolYearId);
    if (userDTO == null) {
      throw new WebApplicationException("User not recognised",
          Response.Status.UNAUTHORIZED);
    }
    if (entrepriseDTO == null) {
      throw new WebApplicationException("Entreprise not recognised",
          Response.Status.UNAUTHORIZED);
    }
    if (schoolYearDTO == null) {
      throw new WebApplicationException("School year not recognised",
          Response.Status.UNAUTHORIZED);
    }

    ContactDTO contactDTO = myContactUCC.createOne(userDTO, entrepriseDTO, schoolYearDTO);
    if (contactDTO == null) {
      throw new WebApplicationException("Error creating a new contact",
          Response.Status.BAD_REQUEST);
    }
    return contactDTO;
  }

  /**
   * Retrieves all contacts associated with one user.
   *
   * @param requestContext the request context
   * @return the list of contacts associated with the user
   */
  @GET
  @Path("allContactsByUserId")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<ContactDTO> getAllContactsByUserId(@Context ContainerRequestContext requestContext) {
    System.out.println("getAllContactsByUserId called");
    UserDTO authentifiedUser = (UserDTO) requestContext.getProperty("user");
    int userId = authentifiedUser.getId();
    return myContactUCC.getAllContactsByUserId(userId);
  }

  /**
   * path to meet a contact.
   *
   * @param json the id and meeting type of the contact.
   * @return the contact updated.
   */
  @PUT
  @Path("met")
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize
  public ContactDTO meetContact(@Context ContainerRequestContext requestContext ,JsonNode json) {
    if (!json.hasNonNull("id_contact") || json.hasNonNull("meetingType")) {
      throw new WebApplicationException("contact id required", Response.Status.BAD_REQUEST);
    }
    int contactId = json.get("id_contact").asInt();
    String meetingType = json.get("meetingType").asText();
    UserDTO user = (UserDTO) requestContext.getProperty("user"); // Conversion en int
    int userId = user.getId();

    return myContactUCC.meetContact(contactId, meetingType,userId);
  }

  /**
   * path to stop following a contact.
   *
   * @param json the id of the contact.
   * @return the contact updated.
   */
  @PUT
  @Path("stopFollow")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ContactDTO stopFollowContact(@Context ContainerRequestContext requestContext ,JsonNode json) {
    if (!json.hasNonNull("id_contact")) {
      throw new WebApplicationException("contact id required", Response.Status.BAD_REQUEST);
    }
    System.out.println("stop follow");
    int contactId = json.get("id_contact").asInt();
    UserDTO user = (UserDTO) requestContext.getProperty("user"); // Conversion en int
    int userId = user.getId();
    System.out.println(userId);
    return myContactUCC.stopFollowContact(contactId,userId);
  }

  /**
   * path to refuse a contact.
   *
   * @param json the id and reason pf refusal of the contact.
   * @return the contact updated.
   */
  @PUT
  @Path("refused")
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize
  public ContactDTO refusedContact(@Context ContainerRequestContext requestContext ,JsonNode json) {
    if (!json.hasNonNull("id_contact") || json.hasNonNull("refusalReason")) {
      throw new WebApplicationException("contact id required", Response.Status.BAD_REQUEST);
    }
    int contactId = json.get("id_contact").asInt();
    String refusalReason = json.get("refusalReason").asText();
    UserDTO user = (UserDTO) requestContext.getProperty("user"); // Conversion en int
    int userId = user.getId();

    return myContactUCC.refusedContact(contactId, refusalReason,userId);
  }
}
