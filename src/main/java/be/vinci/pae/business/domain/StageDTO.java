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
  int getContact();

  /**
   * Sets the contact details of the stage.
   *
   * @param contact The contact details of the stage to be set.
   */
  void setContact(int contact);

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
  int getSupervisor();

  /**
   * Sets the supervisor ID of the stage.
   *
   * @param supervisor The supervisor ID of the stage to be set.
   */
  void setSupervisor(int supervisor);

  /**
   * Retrieves the user ID associated with the stage.
   *
   * @return The user ID associated with the stage.
   */
  int getUser();

  /**
   * Sets the user ID associated with the stage.
   *
   * @param user The user ID associated with the stage to be set.
   */
  void setUser(int user);

  /**
   * Retrieves the school year of the stage.
   *
   * @return The school year of the stage.
   */
  int getSchoolYear();

  /**
   * Sets the school year of the stage.
   *
   * @param schoolYear The school year of the stage to be set.
   */
  void setSchoolYear(int schoolYear);
}
