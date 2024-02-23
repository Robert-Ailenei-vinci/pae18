package be.vinci.pae.controller;

import be.vinci.pae.services.UserDataService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;

public class UserUCCImpl implements UserUCC {

    /**
     * UserDataService (DAO) object by injection
     */
    @Inject
    private UserDataService myUserDataService;

    //Doit return in user dto
    public ObjectNode login (String login, String password){

        if( myUserDataService.getOne(login).checkPassword(password))
        return null;
    }

}
