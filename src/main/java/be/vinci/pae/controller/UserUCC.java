package be.vinci.pae.controller;

import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserDTO;
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
   * @return
   */
  List<User> getAll();
}
