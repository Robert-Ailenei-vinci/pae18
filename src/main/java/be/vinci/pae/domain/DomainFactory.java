package be.vinci.pae.domain;

/**
 * This interface represents a factory for creating domain objects.
 */
public interface DomainFactory {

  /**
   * Returns a new instance of the {@link be.vinci.pae.domain.User} object.
   *
   * @return A new instance of the User object.
   */
  User getUser();
}
