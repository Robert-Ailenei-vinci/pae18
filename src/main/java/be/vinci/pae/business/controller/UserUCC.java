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
   * @param user the user to register.
   * @return true if the user is registered, false if not.
   */
  boolean register(UserDTO user);

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
   * @param version  the version of the user in the frontend
   * @param phoneNum the user phone number.
   * @return the user
   */
  UserDTO changeData(String email, String password, String lname, String fname, String phoneNum,
      int version);

  /**
   * hash the password.
   *
   * @param password the password to hash.
   * @return the hashed password.
   */
  String hashPassword(String password);

  /**
   * Get the number of students with a stage.
   *
   * @param yearID the year id
   * @return the number of students with a stage
   */
  int studsWithStage(int yearID);

  /**
   * Get the number of students without a stage.
   *
   * @param yearID the year id
   * @return the number of students without a stage
   */
  int studsWithNoStage(int yearID);
}
