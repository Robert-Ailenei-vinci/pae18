package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.SupervisorDTO;
import be.vinci.pae.business.domain.UserDTO;
import java.util.List;

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


  /**
   * Retrieves a list of all supervisors for an enterprise.
   *
   * @param entrepriseId enterprise id.
   * @return A list of {@link SupervisorDTO} representing all enterprises.
   */
  List<SupervisorDTO> getAll(int entrepriseId);


  /**
   * Creates a new supervisor.
   *
   * @param user         A {@link UserDTO} representing the user creating the supervisor.
   * @param lastName     The last name of the supervisor.
   * @param firstName    The first name of the supervisor.
   * @param entrepriseId The enterprise ID to which the supervisor belongs.
   * @param phoneNumber  The phone number of the supervisor.
   * @param email        The email of the supervisor.
   * @return A {@link SupervisorDTO} representing the newly created supervisor.
   */
  SupervisorDTO createOne(UserDTO user, String lastName, String firstName, int entrepriseId,
      String phoneNumber, String email);
}