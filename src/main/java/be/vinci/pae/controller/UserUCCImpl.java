package be.vinci.pae.controller;

import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.services.UserDataService;
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
  private UserDataService myUserDataService;

  public UserDTO login(String login, String password) {

    User user = (User) myUserDataService.getOne(login);

    if (user.checkPassword(password)) {
      // return UserDTO
      return user;
    }
    return null;
  }

  public List<User> getAll() {
    return null;
  }

}
