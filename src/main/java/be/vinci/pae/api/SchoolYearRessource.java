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
 * The class {@link SchoolYearRessource} is a RESTful web service that manages school years. It
 * provides endpoints to get all school years and the default school year.
 */
@Singleton
@Path("/schoolYears")
public class SchoolYearRessource {

  /**
   * The {@link SchoolYearUCC} instance used to perform operations related to school years.
   */
  @Inject
  private SchoolYearUCC schoolYearUCC;

  /**
   * Retrieves all school years. This method is accessible only to authorized users.
   *
   * @return a list of all {@link SchoolYearDTO} instances
   */
  @GET
  @Path("getAllSchoolYears")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"etudiant", "professeur", "administratif"})
  public List<SchoolYearDTO> getAllSchoolYears() {
    return schoolYearUCC.getAllSchoolYears();
  }

  /**
   * Retrieves the default school year.
   *
   * @return the default {@link SchoolYearDTO} instance
   */
  @GET
  @Path("getDefaultSchoolYear")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(roles = {"etudiant", "professeur", "administratif"})
  public SchoolYearDTO getDefaultSchoolYear() {
    return schoolYearUCC.getCurrentSchoolYear();
  }
}