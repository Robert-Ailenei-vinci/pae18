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
  String getLastName();

  /**
   * set the name.
   *
   * @param lastName the new name.
   */
  void setLastName(String lastName);

  /**
   * get the first name.
   *
   * @return the first name.
   */
  String getFirstName();

  /**
   * set a new firstname.
   *
   * @param firstName the new firstname.
   */
  void setFirstName(String firstName);

  /**
   * get the phone number.
   *
   * @return the phone number.
   */
  String getPhoneNum();

  /**
   * set a new phone number.
   *
   * @param phoneNum the new phone number.
   */
  void setPhoneNum(String phoneNum);

  /**
   * get the inscription date.
   *
   * @return the inscription date.
   */
  String getRegistrationDate();

  /**
   * set a new inscription date.
   *
   * @param registrationDate the new inscription date
   */
  void setRegistrationDate(String registrationDate);

  /**
   * get the school year id.
   *
   * @return the school year id.
   */
  int getSchooYearId();

  /**
   * set a new school year id.
   *
   * @param id the new school year id.
   */
  void setSchoolYearId(int id);


  void setAcademicYear(String academicYear);

  String getSchoolYear();
}
