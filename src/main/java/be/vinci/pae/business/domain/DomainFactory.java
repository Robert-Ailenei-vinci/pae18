package be.vinci.pae.business.domain;

/**
 * This interface represents a factory for creating domain objects.
 */
public interface DomainFactory {

  /**
   * Returns a new instance of the {@link User} object.
   *
   * @return A new instance of the User object.
   */
  UserDTO getUser();
}
