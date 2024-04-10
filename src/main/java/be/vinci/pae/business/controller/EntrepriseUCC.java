package be.vinci.pae.business.controller;

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
   * Blacklists an enterprise.
   * @param entrepriseId
   * @param reason
   * @return
   */
  EntrepriseDTO blacklist(int entrepriseId, String reason);
}
