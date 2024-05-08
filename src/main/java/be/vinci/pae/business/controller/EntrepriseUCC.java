package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.UserDTO;
import java.util.List;

/**
 * The interface EntrepriseUCC represents the controller for managing {@link EntrepriseDTO}
 * objects.
 */
public interface EntrepriseUCC {

  /**
   * Retrieves the enterprise associated with the specified identifier.
   *
   * @param entrepriseId the identifier of the enterprise to retrieve
   * @return the enterprise associated with the specified identifier
   */
  EntrepriseDTO getOne(int entrepriseId);

  /**
   * Retrieves a list of all enterprises.
   *
   * @return A list of {@link EntrepriseDTO} representing all enterprises.
   */
  List<EntrepriseDTO> getAll();

  /**
   * Creates a new enterprise with the provided details.
   *
   * @param user        the user associated with the enterprise
   * @param tradeName   the trade name of the enterprise
   * @param designation the designation of the enterprise
   * @param address     the address of the enterprise
   * @param phoneNum    the phone number of the enterprise
   * @param email       the email address of the enterprise
   * @return the newly created {@link EntrepriseDTO} representing the enterprise
   */
  EntrepriseDTO createOne(UserDTO user, String tradeName, String designation, String address,
      String phoneNum, String email);

  /**
   * Retrieves all contacts associated with the provided enterprise id.
   *
   * @param entrepriseId The id of the enterprise associated with the contacts.
   * @return The list of contacts associated with the enterprise.
   */
  List<ContactDTO> getAllContactsByEntrepriseId(int entrepriseId);


  /**
   * Blacklists an enterprise.
   *
   * @param entrepriseId id of the entreprise to blacklist
   * @param reason       reason for blacklisting
   * @param version      version of the entreprise
   * @return blacklisted entreprise
   */
  EntrepriseDTO blacklist(int entrepriseId, String reason, int version);

  /**
   * Unblacklists an enterprise.
   *
   * @param entrepriseId id of the entreprise to unblacklist
   * @param version      version of the entreprise
   * @return unblacklisted entreprise
   */
  EntrepriseDTO unblacklist(int entrepriseId, int version);

  /**
   * Retrieves the count of stages for a given school year.
   *
   * @param idEntreprise the entreprise id
   * @return A list of {@link EntrepriseDTO} representing all enterprises for the given school year.
   */
  int getStagesCountForSchoolYear(int idEntreprise);

  /**
   * Retrieves all enterprises for a given school year.
   *
   * @param idSchoolYear the identifier of the school year for which to retrieve enterprises
   * @param orderBy      the column to order the results by
   * @return A list of {@link EntrepriseDTO} representing all enterprises for the given school year.
   */
  List<EntrepriseDTO> getAllForSchoolYear(int idSchoolYear, String orderBy);
}
