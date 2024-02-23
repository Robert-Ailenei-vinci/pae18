package be.vinci.pae.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface UserUCC {
    ObjectNode login (String login, String password);


}
