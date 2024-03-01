package be.vinci.pae.business.domain;

/**
 * This interface represents a user. It defines methods for accessing and modifying user
 * attributes.
 */
public interface User extends UserDTO {


  /**
   * Checks if the provided password matches the user's password stored in the database.
   *
   * @param password The password to be verified.
   * @return True if the provided password matches the user's password, false otherwise.
   */
  boolean checkPassword(String password);

  /**
   * Hashes the provided password.
   *
   * @param password The password to be hashed.
   * @return The hashed password.
   */
  String hashPassword(String password);

  void setSchoolYear(int schoolYear);


}
