package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.services.UserDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

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

  public UserDTO login(String login, String password) {

    User user = (User) myUserDAO.getOne(login);

    if(user == null) {
      throw new WebApplicationException("login or password required", Response.Status.UNAUTHORIZED);
    }

    if (!user.checkPassword(password)) {
     throw new WebApplicationException("login or password required", Response.Status.UNAUTHORIZED);
    }
    return user;
  }

  public List<UserDTO> getAll() {
    return myUserDAO.getAll();
  }

}
