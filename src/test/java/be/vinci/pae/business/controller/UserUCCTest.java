package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.SchoolYear;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.services.DALServices;
import be.vinci.pae.services.SchoolYearDAO;
import be.vinci.pae.services.UserDAO;
import be.vinci.pae.utils.LoggerUtil;
import be.vinci.pae.utils.TestApplicationBinder;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
  private DALServices dalServices;

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
    this.dalServices = mock(DALServices.class);
    this.user = (User) factory.getUser();
    this.existingUser = (User) factory.getUser();
    this.schoolYear = (SchoolYear) factory.getSchoolYear();
    this.expectedUser = (User) factory.getUser();
    this.schoolYearDataService = locator.getService(SchoolYearDAO.class);
  }

  @AfterEach
  public void tearDown() {
    // Clean up resources, reset state, etc.
    Mockito.reset(schoolYearDataService, userDataService, dalServices);
  }


  @DisplayName("Test login")
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

  @DisplayName("Test login with user not found")
  @Test
  public void testLoginUserNotFound() {
    when(userDataService.getOne("testLogin@student.vinci.be")).thenReturn(null);

    assertNull(userUCC.login("testLogin@student.vinci.be", "testPassword"));
  }

  @DisplayName("Test login with wrong password given")
  @Test
  public void testLoginPasswordCheckFails() {
    user.setEmail("testLogin@student.vinci.be");
    user.setPassword(user.hashPassword("wrongPassword"));

    when(userDataService.getOne("testLogin@student.vinci.be")).thenReturn(user);

    assertNull(userUCC.login("testLogin@student.vinci.be", "testPassword"));
  }

  @DisplayName("Test login with transaction error")
  @Test
  public void testLoginException() {
    // Arrange
    String login = "testLogin@student.vinci.be";
    String password = "testPassword";

    // Mock the myUserDAO to throw an exception when getOne is called
    when(userDataService.getOne(anyString())).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(Exception.class, () -> userUCC.login(login, password));
  }

  @DisplayName("Test getAll")
  @Test
  public void testGetAll() {
    List<UserDTO> expectedList = new ArrayList<>();
    expectedList.add(user);
    // Mock the myUserDAO to throw an exception when getAll is called
    when(userDataService.getAll()).thenReturn(expectedList);

    List<UserDTO> actualList = userUCC.getAll();
    assertNotNull(actualList);
    assertEquals(expectedList.size(), actualList.size());
    for (int i = 0; i < expectedList.size(); i++) {
      assertEquals(expectedList.get(i), actualList.get(i));
    }
  }

  @DisplayName("Test register")
  @Test
  public void testRegisterSuccess() {
    user.setFirstName("Loic");
    user.setLastName("Mark");
    user.setEmail("loic.mark@vinci.be");
    user.setPassword("password");
    user.setRole("administratif");

    when(userDataService.getOne(user.getId())).thenReturn(null);
    when(userDataService.addUser(user)).thenReturn(true);

    assertTrue(userUCC.register(user));
  }


  @DisplayName("Test register with already existing user")
  @Test
  public void testRegisterUserAlreadyExists() {
    existingUser.setId(123);
    existingUser.setFirstName("Loic");
    existingUser.setLastName("Mark");
    existingUser.setEmail("loic.mark@vinci.be");
    existingUser.setPassword("password");
    existingUser.setPhoneNum("0485747296");
    existingUser.setRole("administratif");

    when(userDataService.getOne(existingUser.getId())).thenReturn(existingUser);

    user.setId(123);
    user.setEmail("loic.mark@vinci.be");
    user.setPassword("testPassword");

    assertThrows(BizException.class, () -> userUCC.register(user));
  }

  @DisplayName("Test getAll with transaction error")
  @Test
  public void testGetAllException() {
    // Mock the myUserDAO to throw an exception when getAll is called
    when(userDataService.getAll()).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> userUCC.getAll());
  }


  @DisplayName("Test changeData")
  @Test
  public void testChangeData() {

    schoolYear.setId(1);
    schoolYear.setYearFormat("2023-2024");
    String email = "testChangeData@vinci.be";
    String lname = "Test";
    String fname = "User";
    String phoneNum = "1234567890";

    User initialUser = (User) factory.getUser();
    initialUser.setEmail(email);
    initialUser.setPassword("initialPassword");
    initialUser.setLastName(lname);
    initialUser.setFirstName(fname);
    initialUser.setPhoneNum(phoneNum);
    initialUser.setSchoolYear(schoolYear);

    user.setEmail(email);
    user.setPassword("");
    user.setLastName(lname);
    user.setFirstName(fname);
    user.setPhoneNum(phoneNum);
    user.setSchoolYear(schoolYear);
    LoggerUtil.logInfo(user.toString());
    // Mock the getOne method to return the user

    // Mock the changeUser method to return null
    when(userDataService.changeUser(initialUser)).thenReturn(user);
    when(userDataService.getOne(email)).thenReturn(user);

    int version = 1;
    UserDTO result = userUCC.changeData(email, null, lname, fname, phoneNum, version);

    assertNull(result);
  }

  @DisplayName("Test changeData with transaction error")
  @Test
  public void testChangeDataWithWexception() {
    int version = 1;
    String email = "testChangeData@vinci.be";
    String password = "testPassword";
    String lname = "Test";
    String fname = "User";
    String phoneNum = "1234567890";

    // Mock the changeUser method to return null
    when(userDataService.getOne(email)).thenReturn(user);
    when(userUCC.changeData(email, password, lname, fname, phoneNum, version));

    assertThrows(RuntimeException.class,
        () -> userUCC.changeData(email, password, lname, fname, phoneNum, version));
  }

  @DisplayName("Test getOne")
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

  @DisplayName("Test getOne with transaction error")
  @Test
  public void testGetOneWithException() {
    // Arrange
    int userId = 1;

    // Mock the getOne method to return the expectedUser
    when(userUCC.getOne(userId)).thenThrow(new RuntimeException());

    // Assert
    assertThrows(RuntimeException.class, () -> {
      userUCC.getOne(userId);
    });
  }

}