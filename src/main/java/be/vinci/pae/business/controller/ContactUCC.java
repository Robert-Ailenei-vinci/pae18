package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.business.domain.UserDTO;
import java.util.List;

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
   * Retrieves all contacts associated with the provided user id.
   *
   * @param userId The id of the user associated with the contacts.
   * @return The list of contacts associated with the user.
   */
  List<ContactDTO> getAllContactsByUserId(int userId);

  /**
   * set the state of a contact to meet and the type of meeting.
   *
   * @param idContact   the id of the contact to set.
   * @param meetingType the type od the meeting.
   * @param userId      the id of user.
   * @return the contact.
   */
  ContactDTO meetContact(int idContact, String meetingType, int userId);

  /**
   * set the state of a contact to stop followed.
   *
   * @param contactId the id of the contact to set.
   * @param userId    the id of user.
   * @return the contact.
   */
  ContactDTO stopFollowContact(int contactId, int userId);

  /**
   * set the state of a contact to refused and the reason.
   *
   * @param contactId     the id of the contact to set.
   * @param refusalReason the type od the meeting.
   * @param userId        the id of user.
   * @return the contact.
   */
  ContactDTO refusedContact(int contactId, String refusalReason, int userId);
}
