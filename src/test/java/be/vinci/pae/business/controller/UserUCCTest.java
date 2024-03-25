package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.SchoolYear;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.services.SchoolYearDAO;
import be.vinci.pae.services.UserDAO;
import be.vinci.pae.utils.TestApplicationBinder;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@code UserUCC} which tests various functionalities related to user operations.
 * This class contains tests for user login, retrieving all users, handling null user during login,
 * and verifying password during login.
 */
public class UserUCCTest {

  private UserUCC userUCC;
  private User user;
  private User existingUser;
  private DomainFactory factory;
  private UserDAO userDataService;
  private SchoolYearDAO schoolYearDataService;
  private SchoolYear schoolYear;
  private User expectedUser;
  private UserDTO result;

  /**
   * Sets up the environment for each test case by initializing required services and objects. This
   * method binds a test application binder to the service locator, retrieves necessary services,
   * and initializes necessary objects using the domain factory.
   *
   * @throws Exception if an error occurs during setup
   */
  @BeforeEach
  public void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestApplicationBinder());
    this.userUCC = locator.getService(UserUCC.class);
    this.factory = locator.getService(DomainFactory.class);
    this.userDataService = locator.getService(UserDAO.class);
    //utiliser des factory
    this.user = (User) factory.getUser();
    this.existingUser = (User) factory.getUser();
    this.schoolYear = (SchoolYear) factory.getSchoolYear();
    this.expectedUser = (User) factory.getUser();
    this.schoolYearDataService = locator.getService(SchoolYearDAO.class);

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

    assertNull(userUCC.login("testLogin@student.vinci.be", "testPassword"));
  }

  @Test
  public void testLoginPasswordCheckFails() {
    user.setEmail("testLogin@student.vinci.be");
    user.setPassword(user.hashPassword("wrongPassword"));

    when(userDataService.getOne("testLogin@student.vinci.be")).thenReturn(user);

    assertNull(userUCC.login("testLogin@student.vinci.be", "testPassword"));
  }

  @Test
  public void testRegisterSuccess() {

    user.setEmail("test@test.com");
    user.setPassword("password");
    user.setPassword(user.getPassword());

    when(userDataService.addUser(user)).thenReturn(true);

    assertTrue(userUCC.register(user));
  }

  @Test
  public void testRegisterFails() {

    user.setEmail("testFail@test.com");
    user.setPassword("testPassword");

    when(userDataService.addUser(user)).thenReturn(false);

    assertFalse(userUCC.register(user));
  }

  @Test
  public void testRegisterUserAlreadyExists() {
    existingUser.setEmail("existing@test.com");
    existingUser.setPassword("testPassword");

    when(userDataService.getOne(existingUser.getEmail())).thenReturn(existingUser);

    user.setEmail("existing@test.com");
    user.setPassword("testPassword");

    assertThrows(BizException.class, () -> userUCC.register(user));
  }

  @Test
  public void testChangeDataFailed() {
    String email = "testChangeData@test.com";
    String password = "testPassword";
    String lname = "Test";
    String fname = "User";
    String phoneNum = "1234567890";

    schoolYear.setId(1);
    schoolYear.setYearFormat("2023-2024");

    user.setEmail(email);
    user.setPassword(password);
    user.setLastName(lname);
    user.setFirstName(fname);
    user.setPhoneNum(phoneNum);
    user.setSchoolYear(schoolYear);

    // Mock the getOne method to return the user
    when(userDataService.getOne(email)).thenReturn(user);

    // Mock the changeUser method to return null
    when(userDataService.changeUser(user)).thenReturn(null);

    UserDTO result = userUCC.changeData(email, password, lname, fname, phoneNum);

    assertNull(result);
  }

  @Test
  public void testGetOne() {
    // Arrange
    int userId = 1;

    expectedUser.setId(userId);
    expectedUser.setEmail("test@test.com");
    expectedUser.setPassword("password");

    // Mock the getOne method to return the expectedUser
    when(userDataService.getOne(userId)).thenReturn(expectedUser);

    // Act
    UserDTO result = userUCC.getOne(userId);

    // Assert
    assertNotNull(result);
    assertEquals(expectedUser.getId(), result.getId());
    assertEquals(expectedUser.getEmail(), result.getEmail());
    assertEquals(expectedUser.getPassword(), result.getPassword());
  }
}