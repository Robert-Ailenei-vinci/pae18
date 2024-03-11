package be.vinci.pae.business.domain;

/**
 * This interface represents a data transfer object for a supervisor.
 */
public interface SupervisorDTO {

  /**
   * Retrieves the identifier of the supervisor.
   *
   * @return the identifier of the supervisor
   */
  int getSupervisorId();

  /**
   * Sets the identifier of the supervisor.
   *
   * @param supervisorId the identifier to set for the supervisor
   */
  void setSupervisorId(int supervisorId);

  /**
   * Retrieves the last name of the supervisor.
   *
   * @return the last name of the supervisor
   */
  String getLastName();

  /**
   * Sets the last name of the supervisor.
   *
   * @param lastName the last name to set for the supervisor
   */
  void setLastName(String lastName);

  /**
   * Retrieves the first name of the supervisor.
   *
   * @return the first name of the supervisor
   */
  String getFirstName();

  /**
   * Sets the first name of the supervisor.
   *
   * @param firstName the first name to set for the supervisor
   */
  void setFirstName(String firstName);

  /**
   * Retrieves the identifier of the enterprise associated with the supervisor.
   *
   * @return the identifier of the associated enterprise
   */
  int getEntrepriseId();

  /**
   * Sets the identifier of the enterprise associated with the supervisor.
   *
   * @param entrepriseId the identifier of the associated enterprise
   */
  void setEntrepriseId(int entrepriseId);

  /**
   * Retrieves the phone number of the supervisor.
   *
   * @return the phone number of the supervisor
   */
  String getPhoneNumber();

  /**
   * Sets the phone number of the supervisor.
   *
   * @param phoneNumber the phone number to set for the supervisor
   */
  void setPhoneNumber(String phoneNumber);

  /**
   * Retrieves the email of the supervisor.
   *
   * @return the email of the supervisor
   */
  String getEmail();

  /**
   * Sets the email of the supervisor.
   *
   * @param email the email to set for the supervisor
   */
  void setEmail(String email);

  /**
   * Retrieves the enterprise associated with the supervisor.
   *
   * @return the enterprise associated with the supervisor
   */
  EntrepriseDTO getEntreprise();

  /**
   * Sets the enterprise associated with the supervisor.
   *
   * @param entreprise the enterprise to set for the supervisor
   */
  void setEntreprise(EntrepriseDTO entreprise);
}
