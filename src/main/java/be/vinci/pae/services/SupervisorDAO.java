package be.vinci.pae.services;

import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.SupervisorDTO;
import be.vinci.pae.business.domain.UserDTO;

import java.sql.ResultSet;
import java.util.List;

/**
 * The interface SupervisorDAO represents the data access for managing {@link SupervisorDTO}
 * objects.
 */
public interface SupervisorDAO {

  /**
   * Retrieves a supervisor by its id.
   *
   * @param id The id of the supervisor to retrieve.
   * @return The {@link SupervisorDTO} representing the supervisor.
   */
  SupervisorDTO getOneById(int id);

  /**
   * Retrieves a list of all supervisor.
   *
   * @param entrepriseId enterprise id.
   * @return A list of {@link SupervisorDTO} representing all enterprises.
   */
  List<SupervisorDTO> getAll(int entrepriseId);


  /**
   * Creates a supervisor for a given stage
   * *
   * @param user : user, entreprise : entreprise
   * @return The supervisorDTO representing a supervisor
   */
  SupervisorDTO createOne(UserDTO user, EntrepriseDTO entreprise);
}
