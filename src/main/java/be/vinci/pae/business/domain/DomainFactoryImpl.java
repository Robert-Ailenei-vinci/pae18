package be.vinci.pae.business.domain;

/**
 * This class is an implementation of the {@link DomainFactory} interface.
 */
public class DomainFactoryImpl implements DomainFactory {

  @Override
  public UserDTO getUser() {
    return new UserImpl();
  }

  @Override
  public ContactDTO getContact() {
    return new ContactImpl();
  }

  @Override
  public EntrepriseDTO getEntreprise() {
    return new EntrepriseImpl();
  }

  @Override
  public SchoolYearDTO getSchoolYear() {
    return new SchoolYearImpl();
  }

  @Override
  public StageDTO getStage() {
    return new StageImpl();
  }

  @Override
  public SupervisorDTO getSupervisor() {
    return new SupervisorImpl();
  }
}
