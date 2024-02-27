package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.UserDTO;
import java.util.List;

/**
 * interface of UserUcc.
 */
public interface UserUCC {

  /**
   * Login a user.
   *
   * @param login    the users' login.
   * @param password the user password.
   * @return the user , null if not found or password check wrong.
   */
  UserDTO login(String login, String password);


  /**
   * Get All User.
   *
   * @return all user.
   */
  List<UserDTO> getAll();
}
