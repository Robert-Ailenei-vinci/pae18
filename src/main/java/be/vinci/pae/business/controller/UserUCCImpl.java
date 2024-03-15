package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.exception.FatalError;
import be.vinci.pae.exception.UserNotFoundException;
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

    User existingUser = null;
    try {
      existingUser = (User) myUserDAO.getOne(email);
    } catch (UserNotFoundException e) {
      throw new WebApplicationException(e);
    }

    if (existingUser != null) {
      throw new BizException("mail already exist");
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

    try {
      return myUserDAO.getOne(userId);
    } catch (UserNotFoundException e) {
      throw new WebApplicationException(e.getMessage(), e, Response.Status.NOT_FOUND);
    }

  }

  /**
   * Changes user data based on the provided parameters.
   *
   * @param email    The new email for the user.
   * @param password The new password for the user.
   * @param lname    The new last name for the user.
   * @param fname    The new first name for the user.
   * @param phoneNum The new phone number for the user.
   * @return The updated UserDTO object.
   */
  @Override
  public UserDTO changeData(String email, String password, String lname, String fname,
      String phoneNum) {
    User user = (User) myDomainFactory.getUser();
    user.setEmail(email);

    if (password == null) {
      user.setPassword("");
    } else {
      String hashedPassword = user.hashPassword(password);
      user.setPassword(hashedPassword);
    }

    user.setLastName(lname);
    user.setFirstName(fname);
    user.setPhoneNum(phoneNum);
    user.setRegistrationDate(LocalDate.now().toString());

    try {
      myUserDAO.changeUser(user);
    } catch (FatalError e) {
      throw new WebApplicationException(e.getMessage(), e, Response.Status.INTERNAL_SERVER_ERROR);
    }

    return myUserDAO.getOne(email);
  }

}
