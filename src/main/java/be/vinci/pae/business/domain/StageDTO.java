package be.vinci.pae.business.domain;

/**
 * Represents a Data Transfer Object (DTO) for a stage in a school year.
 * This interface defines methods to access and manipulate stage information, including
 * contact details, signature date, internship project, supervisor, user, and school year.
 */
public interface StageDTO {

  /**
   * Retrieves the contact details of the stage.
   *
   * @return The contact details of the stage.
   */
  int getContactId();

  /**
   * Sets the contact details of the stage.
   *
   * @param contactId The contact details of the stage to be set.
   */
  void setContactId(int contactId);

  /**
   * Retrieves the signature date of the stage.
   *
   * @return The signature date of the stage.
   */
  String getSignatureDate();

  /**
   * Sets the signature date of the stage.
   *
   * @param signatureDate The signature date of the stage to be set.
   */
  void setSignatureDate(String signatureDate);

  /**
   * Retrieves the internship project details of the stage.
   *
   * @return The internship project details of the stage.
   */
  String getInternshipProject();

  /**
   * Sets the internship project details of the stage.
   *
   * @param internshipProject The internship project details of the stage to be set.
   */
  void setInternshipProject(String internshipProject);

  /**
   * Retrieves the supervisor ID of the stage.
   *
   * @return The supervisor ID of the stage.
   */
  int getSupervisorId();

  /**
   * Sets the supervisor ID of the stage.
   *
   * @param supervisorId The supervisor ID of the stage to be set.
   */
  void setSupervisorId(int supervisorId);

  /**
   * Retrieves the user ID associated with the stage.
   *
   * @return The user ID associated with the stage.
   */
  int getUserId();

  /**
   * Sets the user ID associated with the stage.
   *
   * @param userId The user ID associated with the stage to be set.
   */
  void setUserId(int userId);

  /**
   * Retrieves the school year of the stage.
   *
   * @return The school year of the stage.
   */
  int getSchoolYearId();

  /**
   * Sets the school year of the stage.
   *
   * @param schoolYearId The school year of the stage to be set.
   */
  void setSchoolYearId(int schoolYearId);

  /**
   * Retrieves the contact associated with the stage.
   *
   * @return The contact associated with the stage.
   */
  ContactDTO getContact();

  /**
   * Sets the contact associated with the stage.
   *
   * @param contactDTO The contact to be associated with the stage.
   */
  void setContact(ContactDTO contactDTO);

  /**
   * Retrieves the user associated with the stage.
   *
   * @return The user associated with the stage.
   */
  UserDTO getUser();

  /**
   * Sets the user associated with the stage.
   *
   * @param user The user to be associated with the stage.
   */
  void setUser(UserDTO user);
  /**
   * Retrieves the school year associated with the stage.
   *
   * @return the school year associated with the stage
   */
  SchoolYearDTO getSchoolYear();

  /**
   * Sets the school year associated with the stage.
   *
   * @param schoolYear the school year associated with the stage
   */
  void setSchoolYear(SchoolYearDTO schoolYear);

  /**
   * Retrieves the supervisor associated with the stage.
   *
   * @return the supervisor associated with the stage
   */
  SupervisorDTO getSupervisor();

  /**
   * Sets the supervisor associated with the stage.
   *
   * @param supervisor the supervisor associated with the stage
   */
  void setSupervisor(SupervisorDTO supervisor);
}
