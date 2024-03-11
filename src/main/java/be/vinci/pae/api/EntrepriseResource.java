package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.controller.EntrepriseUCC;
import be.vinci.pae.business.domain.EntrepriseDTO;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
   * Retrieves an entreprise given it's id. This method is accessed via HTTP GET request to the path
   * "/entreprise/getAll". It returns the list of all enterprises in JSON format. Requires
   * authorization.
   *
   * @return A list of {@link EntrepriseDTO} representing all enterprises.
   */
  @GET
  @Path("getOne")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public EntrepriseDTO getOne(int entrepriseId) {
    return myEntreprise.getOne(entrepriseId);
  }
}
