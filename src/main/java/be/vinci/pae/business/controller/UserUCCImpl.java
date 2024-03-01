package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.services.UserDAO;
import jakarta.inject.Inject;
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
      System.out.println("erroer");
      ;
    }

    if (!user.checkPassword(password)) {
      System.out.printf("eroroer");
    }
    return user;
  }

  public List<UserDTO> getAll() {
    return myUserDAO.getAll();
  }

}
