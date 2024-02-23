package be.vinci.pae.domain;

/**
 * This class is an implementation of the {@link be.vinci.pae.domain.DomainFactory} interface.
 */
public class DomainFactoryImpl implements DomainFactory {

  @Override
  public User getUser() {
    return new UserImpl();
  }
}
