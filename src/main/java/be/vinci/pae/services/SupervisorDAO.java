package be.vinci.pae.services;

import be.vinci.pae.business.domain.SupervisorDTO;

/**
 * The interface SupervisorDAO represents the data access for managing {@link SupervisorDTO} objects.
 */
public interface SupervisorDAO {

  SupervisorDTO getOneById(int id);
}
