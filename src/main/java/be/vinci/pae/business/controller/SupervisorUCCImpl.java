package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.SupervisorDTO;
import be.vinci.pae.services.DALServices;
import be.vinci.pae.services.SupervisorDAO;
import be.vinci.pae.utils.LoggerUtil;
import jakarta.inject.Inject;

public class SupervisorUCCImpl implements SupervisorUCC {

  @Inject
  private SupervisorDAO mySupervisorDAO;
  @Inject
  private DALServices dalServices;

  @Override
  public SupervisorDTO getOneById(int supervisorId) {
    try {
      // Start a new transaction
      dalServices.startTransaction();

      // Retrieve the EntrepriseDTO from the DAO
      SupervisorDTO supervisorDTO = mySupervisorDAO.getOneById(supervisorId);

      // Commit the transaction
      dalServices.commitTransaction();

      return supervisorDTO;
    } catch (Exception e) {
      // Rollback the transaction in case of an error
      LoggerUtil.logError("BizError", e);
      dalServices.rollbackTransaction();
      throw e;
    }
  }
}
