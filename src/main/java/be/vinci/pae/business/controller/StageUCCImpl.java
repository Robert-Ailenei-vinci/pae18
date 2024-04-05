package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.StageDTO;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.exception.BizException;
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

  @Override
  public StageDTO createOne(ContactDTO contact, String signatureDate, String internshipProject,
      int supervisorId) {
    try {
      dalServices.startTransaction();

      if (!((User) contact).checkIsStudent()) {
        throw new BizException(
            "This user is not a student.");
      }
      StageDTO stage = stageDAO.createOne(contact.getId(), signatureDate, internshipProject,
          supervisorId, contact.getUserId(), contact.getSchoolYearId());
      dalServices.commitTransaction();
      return stage;
    } catch (Exception e) {
      LoggerUtil.logError("BizError", e);
      dalServices.rollbackTransaction();
      throw e;
    }
  }
}
