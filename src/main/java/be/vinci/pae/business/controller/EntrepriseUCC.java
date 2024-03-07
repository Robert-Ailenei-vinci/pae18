package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.EntrepriseDTO;

/**
 * The interface EntrepriseUCC represents the controller for managing {@link EntrepriseDTO}
 * objects.
 */
public interface EntrepriseUCC {

  /**
   * Retrieves an EntrepriseDTO object by its identifier.
   *
   * @param entrepriseId the identifier of the entreprise to retrieve
   * @return the EntrepriseDTO corresponding to the identifier, or null
   */
  EntrepriseDTO getOne(int entrepriseId);
}
