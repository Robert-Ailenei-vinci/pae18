package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import be.vinci.pae.services.DALServices;
import be.vinci.pae.services.UserDAO;
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
  private UserDAO userDAO;
  private SchoolYear schoolYear;
  private User expectedUser;
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
    // Arrange
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestApplicationBinder());
    userUCC = locator.getService(UserUCC.class);
    factory = locator.getService(DomainFactory.class);
    userDAO = locator.getService(UserDAO.class);
    dalServices = locator.getService(DALServices.class);
    user = (User) factory.getUser();
    existingUser = (User) factory.getUser();
    schoolYear = (SchoolYear) factory.getSchoolYear();
    expectedUser = (User) factory.getUser();
  }

  @AfterEach
  public void tearDown() {
    // Clean up resources, reset state, etc.
    Mockito.reset(userDAO, dalServices);
  }

  @DisplayName("Test login")
  @Test
  public void testLogin() {
    // Arrange
    user.setEmail("testLogin@student.vinci.be");
    user.setPassword(user.hashPassword("testPassword"));
    when(userDAO.getOne("testLogin@student.vinci.be")).thenReturn(user);

    // Act
    UserDTO result = userUCC.login("testLogin@student.vinci.be", "testPassword");

    // Assert
    assertNotNull(result);
    assertEquals(user.getEmail(), result.getEmail());
    assertEquals(user.getPassword(), result.getPassword());
  }

  @DisplayName("Test login with user not found")
  @Test
  public void testLoginUserNotFound() {
    // Arrange
    when(userDAO.getOne("testLogin@student.vinci.be")).thenReturn(null);

    // Act
    UserDTO result = userUCC.login("testLogin@student.vinci.be", "testPassword");

    // Assert
    assertNull(result);
  }

  @DisplayName("Test login with wrong password given")
  @Test
  public void testLoginPasswordCheckFails() {
    // Arrange
    user.setEmail("testLogin@student.vinci.be");
    user.setPassword(user.hashPassword("wrongPassword"));
    when(userDAO.getOne("testLogin@student.vinci.be")).thenReturn(user);

    // Act & Assert
    assertNull(userUCC.login("testLogin@student.vinci.be", "testPassword"));
  }

  @DisplayName("Test login with exception")
  @Test
  public void testLoginWithException() {
    // Arrange
    when(userDAO.getOne("test")).thenThrow(RuntimeException.class);

    // Act & Assert
    assertThrows(RuntimeException.class, () -> userUCC.login("test", "pass"));
  }

  @DisplayName("Test getAll")
  @Test
  public void testGetAll() {
    // Arrange
    List<UserDTO> expectedList = new ArrayList<>();
    expectedList.add(user);
    when(userDAO.getAll()).thenReturn(expectedList);

    // Act
    List<UserDTO> actualList = userUCC.getAll();

    // Assert
    assertNotNull(actualList);
    assertEquals(expectedList.size(), actualList.size());
    for (int i = 0; i < expectedList.size(); i++) {
      assertEquals(expectedList.get(i), actualList.get(i));
    }
  }

  @DisplayName("Test getAll with exception")
  @Test
  public void testGetAllWithException() {
    // Arrange
    when(userDAO.getAll()).thenThrow(RuntimeException.class);

    // Act & Assert
    assertThrows(RuntimeException.class, () -> userUCC.getAll());
  }

  @DisplayName("Test register")
  @Test
  public void testRegisterSuccess() {
    // Arrange
    user.setFirstName("Loic");
    user.setLastName("Mark");
    user.setEmail("loic.mark@vinci.be");
    user.setPassword("password");
    user.setRole("administratif");
    when(userDAO.getOne(user.getId())).thenReturn(null);
    when(userDAO.addUser(user)).thenReturn(true);

    // Act & Assert
    assertTrue(userUCC.register(user));
  }

  @DisplayName("Test register with already existing user")
  @Test
  public void testRegisterUserAlreadyExists() {
    // Arrange
    existingUser.setId(123);
    existingUser.setFirstName("Loic");
    existingUser.setLastName("Mark");
    existingUser.setEmail("loic.mark@vinci.be");
    existingUser.setPassword("password");
    existingUser.setPhoneNum("0485747296");
    existingUser.setRole("administratif");
    when(userDAO.getOne(existingUser.getId())).thenReturn(existingUser);

    user.setId(123);
    user.setEmail("loic.mark@vinci.be");
    user.setPassword("testPassword");

    // Act & Assert
    assertThrows(BizException.class, () -> userUCC.register(user));
  }

  @DisplayName("Test changeData with empty password")
  @Test
  public void testChangeDataWithEmptyPassword() {
    // Arrange
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

    // Mock the methods
    when(userDAO.changeUser(initialUser)).thenReturn(user);
    when(userDAO.getOne(email)).thenReturn(user);

    // Act
    int version = 1;
    UserDTO result = userUCC.changeData(email, null, lname, fname, phoneNum, version);

    // Assert
    assertNull(result);
  }

  @DisplayName("Test changeData")
  @Test
  public void testChangeData() {
    // Arrange
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
    user.setPassword("testPassword");
    user.setLastName(lname);
    user.setFirstName(fname);
    user.setPhoneNum(phoneNum);
    user.setSchoolYear(schoolYear);

    // Mock the methods
    when(userDAO.changeUser(initialUser)).thenReturn(user);
    when(userDAO.getOne(email)).thenReturn(user);

    // Act
    int version = 1;
    UserDTO result = userUCC.changeData(email, "testPassword", lname, fname, phoneNum, version);

    // Assert
    assertNull(result);
  }

  @DisplayName("Test changeData with exception")
  @Test
  public void testChangeDataWithException() {
    // Arrange
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
    user.setPassword("testPassword");
    user.setLastName(lname);
    user.setFirstName(fname);
    user.setPhoneNum(phoneNum);
    user.setSchoolYear(schoolYear);

    // Mock the methods
    when(userDAO.changeUser(initialUser)).thenReturn(user);
    when(userDAO.getOne(email)).thenThrow(RuntimeException.class);

    // Act & Assert
    assertThrows(RuntimeException.class,
        () -> userUCC.changeData(email, null, lname, fname, phoneNum, 1));
  }

  @DisplayName("Test getOne")
  @Test
  public void testGetOne() {
    // Arrange
    int userId = 1;
    expectedUser.setId(userId);
    expectedUser.setEmail("test@test.com");
    expectedUser.setPassword("password");

    // Mock the method
    when(userDAO.getOne(userId)).thenReturn(expectedUser);

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
    // Arrange & Act & Assert
    int userId = 1;
    when(userUCC.getOne(userId)).thenThrow(new RuntimeException());
    assertThrows(RuntimeException.class, () -> {
      userUCC.getOne(userId);
    });
  }
}