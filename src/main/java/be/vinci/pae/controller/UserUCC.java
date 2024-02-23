package be.vinci.pae.controller;

import be.vinci.pae.domain.User;

public interface UserUCC {



    User login (String login, String password);
}
