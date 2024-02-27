package be.vinci.pae.business.domain;

/**
 * User DTO interface.
 */
public interface UserDTO {


  /**
   * Retrieves the email of the user.
   *
   * @return The email of the user.
   */
  String getEmail();

  /**
   * Sets the email of the user.
   *
   * @param email The new email of the user.
   */
  void setEmail(String email);


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
  void setRole(String role);

  /**
   * get the name.
   *
   * @return the name.
   */
  String getName();

  /**
   * set the name.
   *
   * @param name the new name.
   */
  void setName(String name);

  /**
   * get the first name.
   *
   * @return the first name.
   */
  String getFirstname();

  /**
   * set a new firstname.
   *
   * @param firstname the new firstname.
   */
  void setFirstname(String firstname);

  /**
   * get the phone number.
   *
   * @return the phone number.
   */
  String getPhone_num();

  /**
   * set a new phone number.
   *
   * @param phone_num the new phone number.
   */
  void setPhone_num(String phone_num);

  /**
   * get the inscription date.
   *
   * @return the inscription date.
   */
  String getInscriptionDate();

  /**
   * set a new inscription date.
   *
   * @param inscriptionDate the new inscription date
   */
  void setInscriptionDate(String inscriptionDate);
}
