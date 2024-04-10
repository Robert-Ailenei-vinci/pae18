package be.vinci.pae.services;

import be.vinci.pae.business.domain.Entreprise;
import be.vinci.pae.business.domain.EntrepriseDTO;
import java.util.List;

/**
 * This interface represents a Data Access Object (DAO) for managing entreprise entities.
 */
public interface EntrepriseDAO {

  /**
   * Retrieves an entreprise by its identifier.
   *
   * @param id the identifier of the entreprise to retrieve
   * @return the EntrepriseDTO object corresponding to the provided identifier, or null if no
   *     entreprise with the given identifier exists
   */
  EntrepriseDTO getOne(int id);

  /**
   * Retrieves a list of all enterprises.
   *
   * @return A list of {@link EntrepriseDTO} representing all enterprises.
   */
  List<EntrepriseDTO> getAll();

  /**
   * Creates a new enterprise with the provided details.
   *
   * @param tradeName   the trade name of the enterprise
   * @param designation the designation of the enterprise
   * @param address     the address of the enterprise
   * @param phoneNum    the phone number of the enterprise
   * @param email       the email address of the enterprise
   * @return the newly created {@link EntrepriseDTO} representing the enterprise
   */
  EntrepriseDTO createOne(String tradeName, String designation, String address, String phoneNum,
      String email);

  /**
   * Blacklists an enterprise.
   * @param entreprise
   * @return the newly updated entreprise and it s blacklistred if no errors prior to that
   */
  EntrepriseDTO blacklist(Entreprise entreprise);
}
