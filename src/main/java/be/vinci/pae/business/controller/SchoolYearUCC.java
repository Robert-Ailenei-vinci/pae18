package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.SchoolYearDTO;
import java.util.List;

/**
 * The interface SchoolYearUCC represents the controller for managing {@link SchoolYearDTO}
 * objects.
 */
public interface SchoolYearUCC {

  /**
   * Retrieves the school year associated with the specified identifier.
   *
   * @param schoolYearId the identifier of the school year to retrieve
   * @return the school year associated with the specified identifier
   */
  SchoolYearDTO getOne(int schoolYearId);

  List<SchoolYearDTO> getAllSchoolYears();

  SchoolYearDTO getCurrentSchoolYear();
}
