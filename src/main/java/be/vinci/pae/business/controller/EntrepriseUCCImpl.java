package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.services.EntrepriseDAO;
import jakarta.inject.Inject;
import java.util.List;

/**
 * This class is an implementation of the {@link EntrepriseUCC} interface.
 */
public class EntrepriseUCCImpl implements EntrepriseUCC {

  /**
   * The data access object for enterprise-related operations.
   */
  @Inject
  private EntrepriseDAO myEntrepriseDAO;

  /**
   * Retrieves an EntrepriseDTO object by its identifier.
   *
   * @param entrepriseId the identifier of the entreprise to retrieve
   * @return the EntrepriseDTO object corresponding to the provided identifier, or null if no
   *     entreprise with the given identifier exists
   */
  @Override
  public EntrepriseDTO getOne(int entrepriseId) {
    return myEntrepriseDAO.getOne(entrepriseId);
  }

  /**
   * Retrieves a list of all enterprises.
   *
   * @return A list of {@link EntrepriseDTO} representing all enterprises.
   */
  @Override
  public List<EntrepriseDTO> getAll() {
    return myEntrepriseDAO.getAll();
  }
}
