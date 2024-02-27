package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.services.UserDAO;
import be.vinci.pae.utils.TestApplicationBinder;
import jakarta.ws.rs.WebApplicationException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserUCCTest {

  private UserUCC userUCC;
  private User user;
  private DomainFactory factory;
  private UserDAO userDataService;

  @BeforeEach
  public void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestApplicationBinder());
    this.userUCC = locator.getService(UserUCC.class);
    this.factory = locator.getService(DomainFactory.class);
    this.userDataService = locator.getService(UserDAO.class);
    //utiliser des factory
    this.user = (User) factory.getUser();
  }

  @Test
  public void testLogin() {
    user.setEmail("testLogin@student.vinci.be");
    user.setPassword(user.hashPassword("testPassword"));

    when(userDataService.getOne("testLogin@student.vinci.be")).thenReturn(user);

    UserDTO result = userUCC.login("testLogin@student.vinci.be", "testPassword");

    assertNotNull(result);
    assertEquals(user.getEmail(), result.getEmail());
    assertEquals(user.getPassword(), result.getPassword());
  }

  @Test
  public void testGetAll() {
    assertNotNull(userUCC.getAll());
  }

  @Test
  public void testLoginUserNull() {
    when(userDataService.getOne("testLogin@student.vinci.be")).thenReturn(null);

    assertThrows(WebApplicationException.class, () -> {
      userUCC.login("testLogin@student.vinci.be", "testPassword");
    });
  }

  @Test
  public void testLoginPasswordCheckFails() {
    user.setEmail("testLogin@student.vinci.be");
    user.setPassword(user.hashPassword("wrongPassword"));

    when(userDataService.getOne("testLogin@student.vinci.be")).thenReturn(user);

    assertThrows(WebApplicationException.class, () -> {
      userUCC.login("testLogin@student.vinci.be", "testPassword");
    });
  }

}