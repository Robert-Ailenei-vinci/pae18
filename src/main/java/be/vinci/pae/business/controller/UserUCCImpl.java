package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.services.DALBackServices;
import be.vinci.pae.services.DALServices;
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
  private DALServices dalServices;

  @Inject
  private DALBackServices dalBackServices;

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
    try {
      // Start a new transaction
      dalServices.startTransaction();

      User user = (User) myUserDAO.getOne(login);
      if (user == null) {
        return null;
      }

      if (!user.checkPassword(password)) {
        return null;
      }

      // Commit the transaction
      dalServices.commitTransaction();

      return user;
    } catch (Exception e) {
      // Rollback the transaction in case of an error
      dalServices.rollbackTransaction();
      throw e;
    }
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

    User existingUser;

    try {
      // Start a new transaction
      dalServices.startTransaction();

      existingUser = (User) myUserDAO.getOne(email);
      if (existingUser != null) {
        throw new BizException("User already exists");
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
      boolean result = myUserDAO.addUser(user);

      // Commit the transaction
      dalServices.commitTransaction();
      return result;
    } catch (Exception e) {
      // Rollback the transaction in case of an error
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  @Override
  public List<UserDTO> getAll() {
    try {
      dalServices.startTransaction();
      return myUserDAO.getAll();
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public UserDTO getOne(int userId) {
    try {
      dalServices.startTransaction();
      return myUserDAO.getOne(userId);
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    } finally {
      dalServices.commitTransaction();
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
    try {
      dalServices.startTransaction();
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
      SchoolYearDTO academicYear = myUserDAO.getOne(email).getSchoolYear();
      user.setSchoolYear(academicYear);
      user.setRegistrationDate(LocalDate.now().toString());
      myUserDAO.changeUser(user);

      dalServices.commitTransaction();

      return myUserDAO.getOne(email);
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

}