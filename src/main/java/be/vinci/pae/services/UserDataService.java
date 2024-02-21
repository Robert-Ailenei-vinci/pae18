package be.vinci.pae.services;

import be.vinci.pae.domain.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;

/**
 * UserDataService.
 */
public interface UserDataService {

  /**
   * recup tous les user
   *
   * @return liste de user
   */
  List<User> getAll();

  /**
   * recup un user
   *
   * @param id du user a recup
   * @return le user
   */
  User getOne(int id);

  /**
   * recup un user
   *
   * @param login du user a recup
   * @return le user
   */
  User getOne(String login);

  /**
   * cree un nv user
   *
   * @param item le user a cree
   * @return le user cree
   */
  User createOne(User item);

  /**
   * return le dernier id +1
   *
   * @return le dernier id +1
   */
  int nextItemId();

  /**
   * login
   *
   * @param login    le login
   * @param password mdp
   * @return le user , null si existe pas
   */
  ObjectNode login(String login, String password);

  /**
   * cree un user
   *
   * @param user a cree
   * @return le user cree
   */
  ObjectNode register(User user);
}