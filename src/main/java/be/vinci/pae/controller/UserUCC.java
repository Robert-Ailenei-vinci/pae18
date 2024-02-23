package be.vinci.pae.controller;

import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserDTO;
import java.util.List;

public interface UserUCC {

  UserDTO login(String login, String password);


  List<User> getAll();
}
