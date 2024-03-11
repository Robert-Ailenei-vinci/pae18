package be.vinci.pae.business.domain;

/**
 * This interface represents a factory for creating domain objects.
 */
public interface DomainFactory {

  /**
   * Returns a new instance of the {@link UserDTO} object.
   *
   * @return A new instance of the UserDTO object.
   */
  UserDTO getUser();

  /**
   * Returns a new instance of the {@link ContactDTO} object.
   *
   * @return A new instance of the ContactDTO object.
   */
  ContactDTO getContact();

  /**
   * Returns a new instance of the {@link EntrepriseDTO} object.
   *
   * @return A new instance of the EntrepriseDTO object.
   */
  EntrepriseDTO getEntreprise();

  /**
   * Returns a new instance of the {@link SchoolYearDTO} object.
   *
   * @return A new instance of the SchoolYearDTO object.
   */
  SchoolYearDTO getSchoolYear();

  StageDTO getStage();

  SupervisorDTO getSupervisor();
}
