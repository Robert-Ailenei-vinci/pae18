package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.business.domain.UserDTO;

/**
 * Represents the controller for managing contacts.
 */
public interface ContactUCC {

  /**
   * Creates a new contact.
   *
   * @param user       The user associated with the contact.
   * @param entreprise The enterprise associated with the contact.
   * @param schoolYear The school year associated with the contact.
   * @return The created contact.
   */
  ContactDTO createOne(UserDTO user, EntrepriseDTO entreprise, SchoolYearDTO schoolYear);

  /**
   * set the state of a contact to meet and the type of meeting.
   *
   * @param id_contact  the id of the contact to set.
   * @param meetingType the type od the meeting.
   * @return the contact.
   */
  public ContactDTO meetContact(int id_contact, String meetingType);
}
