package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.EntrepriseDTO;
import java.util.List;

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

  /**
   * Retrieves a list of all enterprises.
   *
   * @return A list of {@link EntrepriseDTO} representing all enterprises.
   */
  List<EntrepriseDTO> getAll();
}
