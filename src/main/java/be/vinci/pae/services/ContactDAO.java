package be.vinci.pae.services;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.business.domain.UserDTO;

/**
 * This interface represents a Data Access Object (DAO) for managing contact entities.
 */
public interface ContactDAO {

  /**
   * Creates a new contact with the provided user, entreprise, and school year.
   *
   * @param user       the user associated with the contact
   * @param entreprise the entreprise associated with the contact
   * @param schoolYear the school year associated with the contact
   * @return the created ContactDTO object
   */
  ContactDTO createOne(UserDTO user, EntrepriseDTO entreprise, SchoolYearDTO schoolYear);

  /**
   * Retrieves the next available item id.
   *
   * @return the next available item id.
   */
  int nextItemId();


  /**
   * Update the state of a contact to meet whith the type of meeting.
   *
   * @param id_contact  id_contact the id of the contact to meet.
   * @param meetingType the type of meeting.
   * @return the updated contact.
   */
  ContactDTO meetContact(int idContact, String meetingType);
}
