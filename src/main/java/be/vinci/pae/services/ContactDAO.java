package be.vinci.pae.services;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.business.domain.UserDTO;
import java.util.List;

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
   * Retrieves all contacts.
   *
   * @param stageId the id of the stage associated with the contact
   * @return the contact associated with the stage
   */
  ContactDTO getOneContactByStageId(int stageId);

  /**
   * Retrieves all contacts associated with the provided user id.
   *
   * @param userId the id of the user associated with the contacts
   * @return the list of contacts associated with the user
   */
  List<ContactDTO> getAllContactsByUserId(int userId);


  /**
   * Update the contact in db.
   *
   * @param contactDTO the contact to update.
   * @return the updated contact.
   */
  ContactDTO updateContact(ContactDTO contactDTO);

  /**
   * get one contact by id.
   *
   * @param idContact the id of contact.
   * @return the contact.
   */
  ContactDTO getOneContactById(int idContact);

  /**
   * get all contacts by entreprise id.
   *
   * @param entrepriseId the id of entreprise.
   * @return the list of contacts.
   */
  List<ContactDTO> getAllContactsByEntrepriseId(int entrepriseId);

  /**
   * cancel the internships if entreprise is blacklisted.
   *
   * @param entrepriseId the id of entreprise.
   * @return true if the internships are canceled.
   */
  boolean cancelInternshipsBasedOnEntrepriseId(int entrepriseId);
}
