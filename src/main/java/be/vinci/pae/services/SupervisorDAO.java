package be.vinci.pae.services;

import be.vinci.pae.business.domain.SupervisorDTO;
import java.util.List;

/**
 * Represents the data access object (DAO) for managing {@link SupervisorDTO} objects.
 */
public interface SupervisorDAO {

  /**
   * Retrieves a supervisor by its ID.
   *
   * @param id The ID of the supervisor to retrieve.
   * @return The {@link SupervisorDTO} representing the supervisor.
   */
  SupervisorDTO getOneById(int id);

  /**
   * Retrieves a list of all supervisors for a given enterprise.
   *
   * @param entrepriseId The ID of the enterprise.
   * @return A list of {@link SupervisorDTO} representing all supervisors for the specified
   *     enterprise.
   */
  List<SupervisorDTO> getAll(int entrepriseId);

  /**
   * Creates a new supervisor in the specified enterprise.
   *
   * @param user       The {@link SupervisorDTO} representing the new supervisor.
   * @param entreprise The ID of the enterprise to which the supervisor will be added.
   * @return The created {@link SupervisorDTO}.
   */
  SupervisorDTO createOne(SupervisorDTO user, int entreprise);
}
