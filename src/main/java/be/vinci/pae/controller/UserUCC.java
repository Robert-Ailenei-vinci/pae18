package be.vinci.pae.controller;

import be.vinci.pae.domain.UserDTO;

public interface UserUCC {

  UserDTO login(String login, String password);


}
