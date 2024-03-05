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
   * Register a user.
   *
   * @param email    the users' login.
   * @param password the user password.
   * @param lname    the user last name.
   * @param fname    the user first name.
   * @param phoneNum the user phone number.
   * @return true if the user is registered, false if not.
   */
  boolean register(String email, String password, String lname, String fname, String phoneNum,
      String role);

  /**
   * Get All User.
   *
   * @return all user.
   */
  List<UserDTO> getAll();
}
