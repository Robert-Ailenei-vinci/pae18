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
   * Creates a supervisor
   *
   * @param user : a user dto, lastName : lastname, firstName : firstname, entrepriseId : entreprise id, phoneNumber : phone number, email : email .
   * @return A {@link SupervisorDTO} representing the created supervisor.
   */
  SupervisorDTO createOne(UserDTO user, String lastName, String firstName, int entrepriseId,
                                 String phoneNumber, String email);
}