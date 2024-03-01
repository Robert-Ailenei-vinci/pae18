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
  List<UserDTO> getAll(); //references

  // UserDTO getOne(int id);

  /**
   * Retrieves a user by their email.
   *
   * @param email The email of the user to retrieve.
   * @return The user with the specified login, or null if not found.
   */
  UserDTO getOne(String email);

  /**
   * Retrieves a list of users with their names.
   *
   * @param pattern A pattern that each users selected has in their names.
   * @return The users with the specified pattern, or null if not found.
   */
  List<UserDTO> getUsersWithNames(String pattern);



  /**
   * Returns the next available item ID.
   *
   * @return The next available item ID.
   */
  int nextItemId();

}