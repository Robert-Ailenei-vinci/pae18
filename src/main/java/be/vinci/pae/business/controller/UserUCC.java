package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.UserDTO;
import java.util.List;

/**
 * The interface UserUCC represents the controller for managing {@link UserDTO} objects.
 */
public interface UserUCC {

  /**
   * Login a user.
   *
   * @param login    the user's login.
   * @param password the user's password.
   * @return the UserDTO object corresponding to the provided login and password, or null if the
   * user is not found or the password check fails
   */
  UserDTO login(String login, String password);


  /**
   * Retrieves all users.
   *
   * @return a list containing all users
   */
  List<UserDTO> getAll();

  /**
   * Retrieves a user by its identifier.
   *
   * @param userId the identifier of the user to retrieve
   * @return the UserDTO object corresponding to the provided identifier, or null if no user with
   * the given identifier exists
   */
  UserDTO getOne(int userId);
}
