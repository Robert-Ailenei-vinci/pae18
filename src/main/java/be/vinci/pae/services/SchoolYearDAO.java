package be.vinci.pae.services;

import be.vinci.pae.business.domain.SchoolYearDTO;
import java.sql.ResultSet;
import java.util.List;

/**
 * This interface represents a Data Access Object (DAO) for managing school year entities.
 */
public interface SchoolYearDAO {

  /**
   * Retrieves a school year by its identifier.
   *
   * @param id the identifier of the school year to retrieve
   * @return the SchoolYearDTO object corresponding to the provided identifier, or null if no school
   *    year with the given identifier exists
   */
  SchoolYearDTO getOne(int id);

  /**
   * Retrieves the current school year.
   *
   * @return the SchoolYearDTO object representing the current school year
   */
  SchoolYearDTO getCurrentSchoolYear();

  /**
   * Builds the current school year.
   *
   * @return the current school year
   */
  String buildYear();
  /**
   * Retrieves all school years.
   *
   * @return a list of all school years
   */
  List<SchoolYearDTO> getAllSchoolYears();
}
