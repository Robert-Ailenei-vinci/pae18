package be.vinci.pae.domain;

public interface UserDTO {


  /**
   * Returns the age of the user.
   *
   * @return The age of the user.
   */
  Integer getAge();

  /**
   * Sets the age of the user.
   *
   * @param age The new age of the user.
   */
  void setAge(Integer age);

  /**
   * Returns true if the user is married, false otherwise.
   *
   * @return True if the user is married, false otherwise.
   */
  Boolean isMarried();

  /**
   * Sets the marital status of the user.
   *
   * @param married The new marital status of the user.
   */
  void setMarried(Boolean married);

  /**
   * Retrieves the login of the user.
   *
   * @return The login of the user.
   */
  String getLogin();

  /**
   * Sets the login of the user.
   *
   * @param login The new login of the user.
   */
  void setLogin(String login);

  /**
   * Retrieves the ID of the user.
   *
   * @return The ID of the user.
   */
  int getId();

  /**
   * Sets the ID of the user.
   *
   * @param id The new ID of the user.
   */
  void setId(int id);

  /**
   * Retrieves the password of the user.
   *
   * @return The password of the user.
   */
  String getPassword();

  /**
   * Sets the password of the user.
   *
   * @param password The new password of the user.
   */
  void setPassword(String password);

  /**
   * Retrieves the role of the user.
   *
   * @return The role of the user.
   */
  String getRole();

  /**
   * Sets the role of the user.
   *
   * @param role The new role of the user.
   */
  String setRole(String role);
}
