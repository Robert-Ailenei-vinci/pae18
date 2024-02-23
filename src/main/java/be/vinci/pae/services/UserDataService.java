package be.vinci.pae.services;

import be.vinci.pae.domain.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
  User getOne(int id);

  /**
   * Retrieves a user by their email.
   *
   * @param email The email of the user to retrieve.
   * @return The user with the specified login, or null if not found.
   */
  User getOne(String email);

  /**
   * Creates a new user.
   *
   * @param item The user to create.
   * @return The created user.
   */
  User createOne(User item);

  /**
   * Returns the next available item ID.
   *
   * @return The next available item ID.
   */
  int nextItemId();

  /**
   * Logs in a user with the provided login and password.
   *
   * @param email    The user's login.
   * @param password The user's password.
   * @return A JSON object representing the user if login is successful, otherwise null.
   */
  ObjectNode login(String email, String password);

  /**
   * Registers a new user.
   *
   * @param user The user to register.
   * @return A JSON object representing the registered user.
   */
  ObjectNode register(User user);
}