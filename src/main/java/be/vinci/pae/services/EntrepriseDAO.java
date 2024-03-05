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
   * @return the EntrepriseDTO object corresponding to the provided identifier, or null if no
   * entreprise with the given identifier exists
   */
  EntrepriseDTO getOne(int id);
}
