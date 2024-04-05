package be.vinci.pae.services;

import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.SupervisorDTO;

/**
 * The interface SupervisorDAO represents the data access for managing
 * {@link SupervisorDTO} objects.
 */
public interface SupervisorDAO {

  /**
   * Retrieves a supervisor by its id.
   *
   * @param id The id of the supervisor to retrieve.
   * @return The {@link SupervisorDTO} representing the supervisor.
   */
  SupervisorDTO getOneById(int id);

  SupervisorDTO createOne(String last_name, String first_name, EntrepriseDTO entreprise, String email, String numero);
}
