package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.services.DALServices;
import be.vinci.pae.services.EntrepriseDAO;
import jakarta.inject.Inject;
import java.util.List;

/**
 * This class is an implementation of the {@link EntrepriseUCC} interface.
 */
public class EntrepriseUCCImpl implements EntrepriseUCC {

  @Inject
  private EntrepriseDAO myEntrepriseDAO;
  @Inject
  private DALServices dalServices;

  @Override
  public EntrepriseDTO getOne(int entrepriseId) {
    try {
      // Start a new transaction
      dalServices.startTransaction();

      // Retrieve the EntrepriseDTO from the DAO
      EntrepriseDTO entrepriseDTO = myEntrepriseDAO.getOne(entrepriseId);

      // Commit the transaction
      dalServices.commitTransaction();

      return entrepriseDTO;
    } catch (Exception e) {
      // Rollback the transaction in case of an error
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  @Override
  public List<EntrepriseDTO> getAll() {
    try {
      // Start a new transaction
      dalServices.startTransaction();

      // Retrieve the list of EntrepriseDTO from the DAO
      List<EntrepriseDTO> entrepriseDTOs = myEntrepriseDAO.getAll();

      // Commit the transaction
      dalServices.commitTransaction();

      return entrepriseDTOs;
    } catch (Exception e) {
      // Rollback the transaction in case of an error
      dalServices.rollbackTransaction();
      throw e;
    }
  }
}
