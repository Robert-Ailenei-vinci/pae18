package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.services.UserDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * implemetation of user UCC.
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
      throw new WebApplicationException("login or password required", Response.Status.UNAUTHORIZED);
    }

    if (!user.checkPassword(password)) {
      throw new WebApplicationException("wrong login or password", Response.Status.UNAUTHORIZED);
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
   * @param role     the user role.
   * @return true if the user is registered, false if not.
   */
  @Override
  public boolean register(String email, String password, String lname, String fname,
      String phoneNum, String role) {
    User user = (User) myDomainFactory.getUser();
    user.setEmail(email);
    String hashedPassword = user.hashPassword(password);
    user.setPassword(hashedPassword);
    user.setLastName(lname);
    user.setFirstName(fname);
    user.setPhoneNum(phoneNum);
    user.setRole(role);
    user.setPhoneNum(phoneNum);
    user.setRegistrationDate(LocalDate.now().toString());
    if (LocalDate.now().getMonth().compareTo(Month.SEPTEMBER) < 0) {
      user.setSchoolYear(
          Integer.parseInt(LocalDate.now() + "-" + LocalDate.now().getYear()));
    }
    user.setSchoolYear(LocalDate.now().getYear() + "-" + (LocalDate.now().getYear() + 1));
    return myUserDAO.addUser(user);
  }

  public List<UserDTO> getAll() {
    return myUserDAO.getAll();
  }

}
