package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.SupervisorDTO;

/**
 * Represents the controller for managing supervisors.
 */
public interface SupervisorUCC {

  /**
   * Retrieves a supervisor by its id.
   *
   * @param supervisorId The id of the supervisor to retrieve.
   * @return The {@link SupervisorDTO} representing the supervisor, or {@code null} if not found.
   */
  SupervisorDTO getOneById(int supervisorId);

}
