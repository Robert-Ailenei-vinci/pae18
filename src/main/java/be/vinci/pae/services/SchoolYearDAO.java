package be.vinci.pae.services;

import be.vinci.pae.business.domain.SchoolYearDTO;

/**
 * This interface represents a Data Access Object (DAO) for managing school year entities.
 */
public interface SchoolYearDAO {

  /**
   * Retrieves a school year by its identifier.
   *
   * @param id the identifier of the school year to retrieve
   * @return the SchoolYearDTO corresponding to the identifier, or null
   */
  SchoolYearDTO getOne(int id);
}
