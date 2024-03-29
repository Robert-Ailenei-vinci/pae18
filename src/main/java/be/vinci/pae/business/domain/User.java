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

  /**
   * Check if student.
   *
   * @return true or false.
   */
  boolean checkIsStudent();

  /**
   * Check if teacher.
   *
   * @return true or false.
   */
  boolean checkIsTeacher();

  /**
   * Check if user exists.
   *
   * @param existingUserDTO the existing user.
   */
  void checkExistingUser(UserDTO existingUserDTO);

  /**
   * Check if register is not empty.
   *
   * @param userDTO the user.
   */
  void checkRegisterNotEmpty(UserDTO userDTO);

  /**
   * Check role from mail.
   *
   * @param email   the email.
   * @param userDTO the user.
   */
  void checkRoleFromMail(String email, UserDTO userDTO);

  /**
   * Check mail.
   *
   * @param email the email.
   */
  void checkMail(String email);

  /**
   * Check mail from lname and fname.
   *
   * @param email the email.
   * @param lname the last name.
   * @param fname the first name.
   */
  void checkmailFromLnameAndFname(String email, String lname, String fname);
}
