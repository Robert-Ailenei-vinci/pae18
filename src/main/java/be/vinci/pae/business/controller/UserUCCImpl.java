package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.services.UserDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;

/**
 * This class represents an implementation of the {@link UserUCC} interface.
 */
public class UserUCCImpl implements UserUCC {

  /**
   * UserDataService (DAO) object by injection.
   */
  @Inject
  private UserDAO myUserDAO;

  @Inject
  private DomainFactory myDomainFactory;

  /**
   * login the user.
   *
   * @param login    the users' login.
   * @param password the user password.
   * @return the user.
   */
  public UserDTO login(String login, String password) {

    User user = (User) myUserDAO.getOne(login);

    if (user == null) {
      return null;
    }

    if (!user.checkPassword(password)) {
      return null;
    }
    return user;
  }

  /**
   * Register a user.
   *
   * @param email    the users' login.
   * @param password the user password.
   * @param lname    the user last name.
   * @param fname    the user first name.
   * @param phoneNum the user phone number.
   * @return true if the user is registered, false if not.
   */
  @Override
  public boolean register(String email, String password, String lname, String fname,
      String phoneNum, String role) {
    User existingUser = (User) myUserDAO.getOne(email);
    if (existingUser != null) {
      throw new WebApplicationException("An account with this email already exists",
          Response.Status.BAD_REQUEST);
    }
    User user = (User) myDomainFactory.getUser();
    user.setEmail(email);
    String hashedPassword = user.hashPassword(password);
    user.setPassword(hashedPassword);
    user.setLastName(lname);
    user.setFirstName(fname);
    user.setPhoneNum(phoneNum);
    user.setRegistrationDate(LocalDate.now().toString());
    user.setRole(role);
    return myUserDAO.addUser(user);
  }

  @Override
  public List<UserDTO> getAll() {
    return myUserDAO.getAll();
  }

  @Override
  public UserDTO getOne(int userId) {
    return myUserDAO.getOne(userId);
  }

}
