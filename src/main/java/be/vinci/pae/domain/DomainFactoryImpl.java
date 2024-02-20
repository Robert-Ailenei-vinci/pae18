package be.vinci.pae.domain;

public class DomainFactoryImpl implements DomainFactory {

  @Override
  public User getUser() {
    return new UserImpl();
  }
}
