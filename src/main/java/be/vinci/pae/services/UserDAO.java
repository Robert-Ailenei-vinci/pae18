package be.vinci.pae.services;

import be.vinci.pae.business.domain.UserDTO;
import java.util.List;

/**
 * This interface provides methods for interacting with user data.
 */
public interface UserDAO {

  /**
   * Retrieves all users.
   *
   * @return A list of all users.
   */
  List<UserDTO> getAll();
  
//  UserDTO getOne(int id);

  /**
   * Retrieves a user by their email.
   *
   * @param email The email of the user to retrieve.
   * @return The user with the specified login, or null if not found.
   */
  UserDTO getOne(String email);


  /**
   * Returns the next available item ID.
   *
   * @return The next available item ID.
   */
  int nextItemId();

}