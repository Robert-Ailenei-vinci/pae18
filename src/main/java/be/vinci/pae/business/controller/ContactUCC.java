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
   * Sets the state of a contact to "meeting" and the type of meeting.
   *
   * @param idContact   The id of the contact to set.
   * @param meetingType The type of the meeting.
   * @param userId      The id of the user.
   * @param version     The version of the contact in the frontend.
   * @return The updated contact.
   */
  ContactDTO meetContact(int idContact, String meetingType, int userId, int version);

  /**
   * Sets the state of a contact to "stop followed".
   *
   * @param contactId The id of the contact to set.
   * @param userId    The id of the user.
   * @param version   The version of the contact in the frontend.
   * @return The updated contact.
   */
  ContactDTO stopFollowContact(int contactId, int userId, int version);

  /**
   * Sets the state of a contact to "refused" and provides the reason.
   *
   * @param contactId     The id of the contact to set.
   * @param refusalReason The reason for refusal.
   * @param userId        The id of the user.
   * @param version       The version of the contact in the frontend.
   * @return The updated contact.
   */
  ContactDTO refusedContact(int contactId, String refusalReason, int userId, int version);


  /**
   * set all internships of a contact to refusé if an entreprise is blacklisted.
   *
   * @param idEntreprise the id of the entreprise.
   * @return true if the internships are canceled.
   */
  boolean cancelInternshipsBasedOnEntreprise(int idEntreprise);

  /**
   * Sets the state of a contact to "accepted".
   *
   * @param contactId The id of the contact to set.
   * @param userId    The id of the user.
   * @param version   The version of the contact in the frontend.
   * @return The updated contact.
   */
  ContactDTO acceptContact(int contactId, int userId, int version,int supervisorId,String signatureDate,String internshipProject);
}
