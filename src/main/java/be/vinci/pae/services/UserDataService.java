package be.vinci.pae.services;

import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserDTO;
import java.util.List;

/**
 * This interface provides methods for interacting with user data.
 */
public interface UserDataService {

  /**
   * Retrieves all users.
   *
   * @return A list of all users.
   */
  List<User> getAll();

  /**
   * Retrieves a user by their ID.
   *
   * @param id The ID of the user to retrieve.
   * @return The user with the specified ID, or null if not found.
   */
  UserDTO getOne(int id);

  /**
   * Retrieves a user by their email.
   *
   * @param email The email of the user to retrieve.
   * @return The user with the specified login, or null if not found.
   */
  UserDTO getOne(String email);

  /**
   * Creates a new user.
   *
   * @param item The user to create.
   * @return The created user.
   */
  UserDTO createOne(User item);

  /**
   * Returns the next available item ID.
   *
   * @return The next available item ID.
   */
  int nextItemId();

}