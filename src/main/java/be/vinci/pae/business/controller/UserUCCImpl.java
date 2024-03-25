package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.exception.BizException;
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
   * @param userDTO the user to register.
   * @return true if the user is registered, false if not.
   */

  @Override
  public boolean register(UserDTO userDTO) {
    try {
      dalServices.startTransaction();

      UserDTO existingUserDTO = myUserDAO.getOne(userDTO.getEmail());
      if (existingUserDTO != null) {
        throw new BizException("User already exists");
      }

      User user = (User) myDomainFactory.getUser();
      user.setPassword(userDTO.getPassword());
      String hashedPassword = user.hashPassword(user.getPassword());
      userDTO.setPassword(hashedPassword);
      userDTO.setRegistrationDate(LocalDate.now().toString());

      boolean result = myUserDAO.addUser(userDTO);

      dalServices.commitTransaction();

      return result;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  @Override
  public List<UserDTO> getAll() {
    try {
      dalServices.startTransaction();
      List<UserDTO> users = myUserDAO.getAll();
      dalServices.commitTransaction();
      return users;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  @Override
  public UserDTO getOne(int userId) {
    try {
      dalServices.startTransaction();
      UserDTO user = myUserDAO.getOne(userId);
      dalServices.commitTransaction();
      return user;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

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

      UserDTO updatedUser = myUserDAO.changeUser(user);
      if (updatedUser == null) {
        return null;
      }

      dalServices.commitTransaction();

      return updatedUser;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }


}
