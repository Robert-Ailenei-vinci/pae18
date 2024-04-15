package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.controller.SchoolYearUCC;
import be.vinci.pae.business.domain.SchoolYearDTO;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

/**
 * Ressource to manage school years.
 */
@Singleton
@Path("/schoolYears")
public class SchoolYearRessource {

  @Inject
  private SchoolYearUCC schoolYearUCC;

  @GET
  @Path("getAllSchoolYears")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<SchoolYearDTO> getAllSchoolYears() {
    return schoolYearUCC.getAllSchoolYears();
  }

  @GET
  @Path("getDefaultSchoolYear")
  @Produces(MediaType.APPLICATION_JSON)
  public SchoolYearDTO getDefaultSchoolYear() {
    return schoolYearUCC.getCurrentSchoolYear();
  }
}