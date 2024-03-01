package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.business.domain.UserDTO;

public interface ContactUCC {
    ContactDTO newContact(UserDTO userDTO, ContactDTO contactDTO, SchoolYearDTO schoolYearDTO);
}
