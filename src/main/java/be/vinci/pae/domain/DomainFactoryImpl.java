package be.vinci.pae.domain;

/**
 * DomainFactoryImpl.
 */
public class DomainFactoryImpl implements DomainFactory {

  @Override
  public User getUser() {
    return new UserImpl();
  }
}
