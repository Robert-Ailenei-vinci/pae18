package be.vinci.pae.business.domain;

/**
 * This class is an implementation of the {@link DomainFactory} interface.
 */
public class DomainFactoryImpl implements DomainFactory {

  @Override
  public UserDTO getUser() {
    return new UserImpl();
  }
}
