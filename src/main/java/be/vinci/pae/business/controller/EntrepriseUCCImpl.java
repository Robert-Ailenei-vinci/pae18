package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.Entreprise;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.services.ContactDAO;
import be.vinci.pae.services.DALServices;
import be.vinci.pae.services.EntrepriseDAO;
import be.vinci.pae.services.StageDAO;
import be.vinci.pae.utils.LoggerUtil;
import jakarta.inject.Inject;
import java.util.List;

/**
 * This class is an implementation of the {@link EntrepriseUCC} interface.
 */
public class EntrepriseUCCImpl implements EntrepriseUCC {

  @Inject
  private EntrepriseDAO myEntrepriseDAO;
  @Inject
  private ContactDAO contactDAO;
  @Inject
  private StageDAO stageDAO;
  @Inject
  private DALServices dalServices;


  /**
   * Retrieves an EntrepriseDTO object by its identifier.
   *
   * @param entrepriseId the identifier of the entreprise to retrieve
   * @return the EntrepriseDTO object corresponding to the provided identifier, or null
   */
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

  /**
   * Retrieves a list of all enterprises.
   *
   * @return A list of {@link EntrepriseDTO} representing all enterprises.
   */
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

  @Override
  public EntrepriseDTO createOne(UserDTO user, String tradeName, String designation, String address,
      String phoneNum, String email) {
    try {
      dalServices.startTransaction();

      if (!((User) user).checkIsStudent()) {
        throw new BizException(
          "This user is not a student.");
      }
      Entreprise entreprise = (Entreprise) myEntrepriseDAO.createOne(tradeName, designation,
          address, phoneNum, email);

      dalServices.commitTransaction();

      return entreprise;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  @Override
  public List<ContactDTO> getAllContactsByEntrepriseId(int entrepriseId) {
    try {
      dalServices.startTransaction();
      List<ContactDTO> contacts = contactDAO.getAllContactsByEntrepriseId(entrepriseId);
      dalServices.commitTransaction();
      return contacts;
    } catch (Exception e) {
      LoggerUtil.logError("BizError", e);
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  /**
   * Blacklists an enterprise.
   *
   * @param entrepriseId id of the entreprise to blacklist
   * @param reason      reason for blacklisting
   * @return blacklisted entreprise
   */
  @Override
  public EntrepriseDTO blacklist(int entrepriseId, String reason, int version) {
    try {
      dalServices.startTransaction();

      Entreprise entreprise = (Entreprise) myEntrepriseDAO.getOne(entrepriseId);
      entreprise.setIsBlacklisted(true);
      entreprise.setBlacklistReason(reason);

      dalServices.commitTransaction();
      EntrepriseDTO updatedEntreprise = myEntrepriseDAO.blacklist(entreprise, version);
      return updatedEntreprise;
    } catch (Exception e) {
      LoggerUtil.logError("BizError", e);
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  /**
   * Unblacklists an enterprise.
   *
   * @param entrepriseId id of the entreprise to unblacklist
   * @return unblacklisted entreprise
   */
  @Override
  public EntrepriseDTO unblacklist(int entrepriseId, int version) {
    try {
      dalServices.startTransaction();

      Entreprise entreprise = (Entreprise) myEntrepriseDAO.getOne(entrepriseId);
      entreprise.setIsBlacklisted(false);
      entreprise.setBlacklistReason(null);

      dalServices.commitTransaction();
      EntrepriseDTO updatedEntreprise = myEntrepriseDAO.unblacklist(entreprise, version);
      return updatedEntreprise;
    } catch (Exception e) {
      LoggerUtil.logError("BizError", e);
      dalServices.rollbackTransaction();
      throw e;
    }
  }


  @Override
  public List<EntrepriseDTO> getAllForSchoolYear(int idSchoolYear) {
    try {
      dalServices.startTransaction();
      List<EntrepriseDTO> entreprises = myEntrepriseDAO.getAllForSchoolYear(idSchoolYear);
      dalServices.commitTransaction();
      return entreprises;
    } catch (Exception e) {
      LoggerUtil.logError("BizError", e);
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  @Override
  public int getStagesCountForSchoolYear(int idEntreprise) {
    try {
      dalServices.startTransaction();
      int count = myEntrepriseDAO.getNbStagesForCurrentYear(idEntreprise);
      dalServices.commitTransaction();
      return count;
    } catch (Exception e) {
      LoggerUtil.logError("BizError", e);
      dalServices.rollbackTransaction();
      throw e;
    }
  }
}
