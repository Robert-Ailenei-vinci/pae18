package be.vinci.pae.services;

import be.vinci.pae.business.domain.EntrepriseDTO;

/**
 * This interface represents a Data Access Object (DAO) for managing entreprise entities.
 */
public interface EntrepriseDAO {

  /**
   * Retrieves an entreprise by its identifier.
   *
   * @param id the identifier of the entreprise to retrieve
   * @return the EntrepriseDTO object corresponding to the identifier, or null
   */
  EntrepriseDTO getOne(int id);
}
