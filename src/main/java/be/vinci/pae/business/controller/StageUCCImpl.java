package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.StageDTO;
import be.vinci.pae.services.DALServices;
import be.vinci.pae.services.StageDAO;
import be.vinci.pae.utils.LoggerUtil;
import jakarta.inject.Inject;

/**
 * This class is an implementation of the {@link StageUCC} interface.
 */
public class StageUCCImpl implements StageUCC {

  @Inject
  private StageDAO stageDAO;
  @Inject
  private DALServices dalServices;

  @Override
  public StageDTO getOneStageByUserId(int userId) {
    try {
      dalServices.startTransaction();
      StageDTO stageDTO = stageDAO.getOneStageByUserId(userId);
      dalServices.commitTransaction();
      return stageDTO;
    } catch (Exception e) {
      LoggerUtil.logError("BizError", e);
      dalServices.rollbackTransaction();
      throw e;
    }
  }


}
