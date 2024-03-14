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
   * @param login    the users' login.
   * @param password the user password.
   * @return the user
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
   * @param role     the user role.
   * @return true if the user is registered, false if not.
   */
  boolean register(String email, String password, String lname, String fname, String phoneNum,
      String role);

  /**
   * Get All User.
   *
   * @return a list containing all users
   */
  List<UserDTO> getAll();

  /**
   * Get one user.
   *
   * @param userId the user id
   * @return the user
   */
  UserDTO getOne(int userId);


  /**
   * Change the data of a user.
   *
   * @param email    the users' login.
   * @param password the user password.
   * @param lname    the user last name.
   * @param fname    the user first name.
   * @param phoneNum the user phone number.
   * @return the user
   */
  UserDTO changeData(String email, String password, String lname, String fname, String phoneNum);
}
