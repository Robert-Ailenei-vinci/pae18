package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.exception.UserNotFoundException;
import be.vinci.pae.services.UserDAO;
import jakarta.inject.Inject;
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
   * @param userDTO the user to register.
   * @return true if the user is registered, false if not.
   */

  @Override
  public boolean register(UserDTO userDTO) {

    User user = (User) myDomainFactory.getUser();
    user.checkRegisterNotEmpty(userDTO);

    // Check if user already exists
    User existingUser = null;
    try {
      existingUser = (User) myUserDAO.getOne(userDTO.getEmail());
    } catch (UserNotFoundException e) {
      // User does not exist, so we can continue with registration
    }

    user.checkExisitngUser(existingUser);
    user.checkMail(userDTO.getEmail());
    user.checkRoleFromMail(userDTO.getEmail(), userDTO);
    user.setPassword(hashPassword(userDTO.getPassword()));
    userDTO.setPassword(user.getPassword());
    userDTO.setRegistrationDate(LocalDate.now().toString());

    return myUserDAO.addUser(userDTO);
  }

  public String hashPassword(String password) {
    User user = (User) myDomainFactory.getUser();
    return user.hashPassword(password);
  }

  @Override
  public List<UserDTO> getAll() {
    return myUserDAO.getAll();
  }

  @Override
  public UserDTO getOne(int userId) {
    return myUserDAO.getOne(userId);
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
    user.checkMail(email);
    user.setEmail(email);

    if (password == null) {
      user.setPassword("");
    } else {
      //did this because if I don't want to change psw, it will be null, look at dao if's
      String hashedPassword = user.hashPassword(password);
      user.setPassword(hashedPassword);
    }

    user.setLastName(lname);
    user.setFirstName(fname);
    user.setPhoneNum(phoneNum);
    SchoolYearDTO academicYear = myUserDAO.getOne(email).getSchoolYear();
    user.setSchoolYear(academicYear);
    user.setRegistrationDate(LocalDate.now().toString());
    if (myUserDAO.changeUser(user) == null) {
      return null;
    }
    return myUserDAO.getOne(email);
  }


}
