package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.SchoolYearDTO;

/**
 * The interface SchoolYearUCC represents the controller for managing {@link SchoolYearDTO}
 * objects.
 */
public interface SchoolYearUCC {

  /**
   * Retrieves a {@link SchoolYearDTO} object by its identifier.
   *
   * @param schoolYearId the identifier of the school year to retrieve
   * @return the {@link SchoolYearDTO} corresponding to the identifier, or null
   */
  SchoolYearDTO getOne(int schoolYearId);
}
