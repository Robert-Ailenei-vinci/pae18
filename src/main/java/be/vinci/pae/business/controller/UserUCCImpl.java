package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.services.DALServices;
import be.vinci.pae.services.UserDAO;
import be.vinci.pae.utils.LoggerUtil;
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

  @Override
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
      LoggerUtil.logError("BizError", e);
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  @Override
  public boolean register(UserDTO userDTO) {
    try {
      dalServices.startTransaction();

      User user = (User) myDomainFactory.getUser();
      user.checkRegisterNotEmpty(userDTO);

      // Check if user already exists
      User existingUser = (User) myUserDAO.getOne(userDTO.getEmail());
      user.checkExistingUser(existingUser);
      user.checkmailFromLnameAndFname(userDTO.getEmail(), userDTO.getLastName(),
          userDTO.getFirstName());

      user.checkMail(userDTO.getEmail());
      user.checkRoleFromMail(userDTO.getEmail(), userDTO);
      user.setPassword(hashPassword(userDTO.getPassword()));
      userDTO.setPassword(user.getPassword());
      userDTO.setRegistrationDate(LocalDate.now().toString());
      userDTO.setVersion(0);

      boolean result = myUserDAO.addUser(userDTO);

      dalServices.commitTransaction();

      return result;
    } catch (Exception e) {
      LoggerUtil.logError("BizError", e);
      dalServices.rollbackTransaction();
      throw e;
    }

  }

  public String hashPassword(String password) {
    User user = (User) myDomainFactory.getUser();
    return user.hashPassword(password);
  }

  @Override
  public List<UserDTO> getAll() {
    try {
      dalServices.startTransaction();
      List<UserDTO> users = myUserDAO.getAll();
      dalServices.commitTransaction();
      return users;
    } catch (Exception e) {
      LoggerUtil.logError("BizError", e);
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
      LoggerUtil.logError("BizError", e);
      dalServices.rollbackTransaction();
      throw e;
    }
  }


  @Override
  public UserDTO changeData(String email, String password, String lname, String fname,
      String phoneNum, int version) {
    try {
      dalServices.startTransaction();

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
      user.setVersion(version);
      SchoolYearDTO academicYear = myUserDAO.getOne(email).getSchoolYear();
      user.setSchoolYear(academicYear);
      user.setRegistrationDate(LocalDate.now().toString());
      UserDTO updatedUser = myUserDAO.changeUser(user);

      dalServices.commitTransaction();

      return updatedUser;
    } catch (Exception e) {
      LoggerUtil.logError("BizError", e);
      dalServices.rollbackTransaction();
      throw e;
    }
  }
}
