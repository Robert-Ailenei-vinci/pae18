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
   * @return the SchoolYearDTO object corresponding to the provided identifier, or null if no school
   * year with the given identifier exists
   */
  SchoolYearDTO getOne(int id);
}
