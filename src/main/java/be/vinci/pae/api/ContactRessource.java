package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.domain.ContactDTO;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Singleton
@Path("/contacts")
public class ContactRessource {

  @POST
  @Path("add")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ContactDTO addContact(JsonNode json) {

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
