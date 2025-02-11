package be.vinci.pae.services;

import be.vinci.pae.business.domain.UserDTO;
import java.util.List;


/**
 * This interface provides methods for interacting with user data.
 */
public interface UserDAO {

  /**
   * Retrieves all the users.
   *
   * @return A list of all users.
   */
  List<UserDTO> getAll();

  /**
   * Retrieves a user by their email.
   *
   * @param email The email of the user to retrieve.
   * @return The user with the specified login, or null if not found.
   */
  UserDTO getOne(String email);

  /**
   * Retrieves a user by their id.
   *
   * @param id The id of the user to retrieve.
   * @return The user with the specified id, or null if not found.
   */
  UserDTO getOne(int id);

  /**
   * Adds a user to the database.
   *
   * @param user The user to add.
   * @return True if the user was added, false if not.
   */
  boolean addUser(UserDTO user);


  /**
   * Change user data.
   *
   * @param user The user to change.
   * @return The user with the changed data
   */
  UserDTO changeUser(UserDTO user);

  /**
   * Retrieves the number of the students who has a stage.
   *
   * @param yearID the id of the year
   * @return A list of all students with their stage.
   */
  int studsWithStage(int yearID);

  /**
   * Retrieves the number of the students who have no stage.
   *
   * @param yearID the id of the year
   * @return A list of all students with no stage.
   */
  int studsWithNoStage(int yearID);
}