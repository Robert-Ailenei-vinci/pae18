package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.controller.ContactUCC;
import be.vinci.pae.business.domain.ContactDTO;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Represents a RESTful API resource for managing contacts.
 */
@Singleton
@Path("/contacts")
public class ContactRessource {

  @Inject
  private ContactUCC myContactUCC;

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
    /*    if (!json.hasNonNull("user")
                || !json.hasNonNull("entreprise")
                || !json.hasNonNull("schoolYear")) {
            throw new WebApplicationException("user, entreprise or schoolYear required",
                    Response.Status.BAD_REQUEST);
        }
        int userId = json.get("user").asInt();
        int entrepriseId = json.get("entreprise").asInt();
        int schoolYearId = json.get("schooYear").asInt();

        // Try to get user, entreprise, and school year
    UserDTO userDTO = UserUCC.getOne(userId);
    EntrepriseDTO entrepriseDTO = EntrepriseUCC.getOne(entrepriseId);
    SchoolYearDTO schoolYearDTO = SchoolYearUCC.getOne(schoolYearId);
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

    ContactDTO contactDTO = ContactUCC.createOne(userDTO, entrepriseDTO, schoolYearDTO);
    if (contactDTO == null) {
      throw new WebApplicationException("Error creating a new contact",
          Status.BAD_REQUEST);
    }
    return contactDTO;*/
    return null;
  }

  @PUT
  @Path("met")
  @Consumes(MediaType.APPLICATION_JSON)
  public ContactDTO meetContact(JsonNode json) {
    //Get and check contact id
    if (!json.hasNonNull("id_contact")) {
      throw new WebApplicationException("contact id required", Response.Status.BAD_REQUEST);
    }
    String contactId = json.get("id_contact").asText();

    return null;
  }
}
