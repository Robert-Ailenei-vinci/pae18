package be.vinci.pae.business.domain;

/**
 * The interface {@link ContactDTO} represents the data transfer object for managing contacts.
 */
public interface ContactDTO {

  /**
   * Retrieves the identifier of the contact.
   *
   * @return the identifier of the contact
   */
  int getId();

  /**
   * Sets the identifier of the contact.
   *
   * @param id the identifier to set for the contact
   */
  void setId(int id);

  /**
   * Retrieves the state of the contact.
   *
   * @return the state of the contact
   */
  String getState();

  /**
   * Sets the state of the contact.
   *
   * @param state the state to set for the contact
   */
  void setState(String state);

  /**
   * Retrieves the identifier of the user associated with the contact.
   *
   * @return the identifier of the user associated with the contact
   */
  int getUserId();

  /**
   * Sets the identifier of the user associated with the contact.
   *
   * @param user the identifier of the user to set for the contact
   */
  void setUserId(int user);

  /**
   * Retrieves the identifier of the entreprise associated with the contact.
   *
   * @return the identifier of the entreprise associated with the contact
   */
  int getEntrepriseId();

  /**
   * Sets the identifier of the entreprise associated with the contact.
   *
   * @param entreprise the identifier of the entreprise to set for the contact
   */
  void setEntrepriseId(int entreprise);

  /**
   * Retrieves the identifier of the school year associated with the contact.
   *
   * @return the identifier of the school year associated with the contact
   */
  int getSchoolYearId();

  /**
   * Sets the identifier of the school year associated with the contact.
   *
   * @param schoolYearId the identifier of the school year to set for the contact
   */
  void setSchoolYearId(int schoolYearId);

  /**
   * Retrieves the reason for refusal associated with the contact.
   *
   * @return the reason for refusal associated with the contact
   */
  String getReasonForRefusal();

  /**
   * Sets the reason for refusal associated with the contact.
   *
   * @param reasonForRefusal the reason for refusal to set for the contact
   */
  void setReasonForRefusal(String reasonForRefusal);

  /**
   * Retrieves the meeting type associated with the contact.
   *
   * @return the meeting type associated with the contact
   */
  String getMeetingType();

  /**
   * Sets the meeting type associated with the contact.
   *
   * @param meetingType the meeting type to set for the contact
   */
  void setMeetingType(String meetingType);

  /**
   * Retrieves the user associated with the contact.
   *
   * @return the user associated with the contact
   */
  EntrepriseDTO getEntreprise();

  /**
   * Sets the user associated with the contact.
   *
   * @param entreprise the user to set for the contact
   */
  void setEntreprise(EntrepriseDTO entreprise);
}
