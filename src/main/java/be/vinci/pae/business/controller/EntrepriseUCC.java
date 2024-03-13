package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.EntrepriseDTO;

/**
 * The interface EntrepriseUCC represents the controller for managing {@link EntrepriseDTO}
 * objects.
 */
public interface EntrepriseUCC {

  /**
   * Retrieves the enterprise associated with the specified identifier.
   *
   * @param entrepriseId the identifier of the enterprise to retrieve
   * @return the enterprise associated with the specified identifier
   */
  EntrepriseDTO getOne(int entrepriseId);
}
