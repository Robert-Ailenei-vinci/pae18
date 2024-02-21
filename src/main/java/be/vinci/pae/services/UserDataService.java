package be.vinci.pae.services;

import be.vinci.pae.domain.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;

/**
 * UserDataService
 */
public interface UserDataService {

  List<User> getAll();

  User getOne(int id);

  User getOne(String login);

  User createOne(User item);

  int nextItemId();

  ObjectNode login(String login, String password);

  ObjectNode register(User user);
}