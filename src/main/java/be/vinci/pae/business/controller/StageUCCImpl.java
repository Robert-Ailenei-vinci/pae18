package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.Stage;
import be.vinci.pae.business.domain.StageDTO;
import be.vinci.pae.services.DALServices;
import be.vinci.pae.services.StageDAO;
import jakarta.inject.Inject;

/**
 * This class is an implementation of the {@link StageUCC} interface.
 */
public class StageUCCImpl implements StageUCC {

  @Inject
  private StageDAO stageDAO;
  @Inject
  private DALServices dalServices;

  @Inject
  private DomainFactory myDomainFactory;


  @Override
  public StageDTO getOneStageByUserId(int userId) {
    try {
      dalServices.startTransaction();
      StageDTO stageDTO = stageDAO.getOneStageByUserId(userId);
      dalServices.commitTransaction();
      return stageDTO;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  @Override
  public StageDTO modifyStage(int userId, String subject, int contactId, int version) {
    try {
      dalServices.startTransaction();

      Stage stage = (Stage) myDomainFactory.getStage();

      stage.setUserId(userId);
      stage.setContactId(contactId);
      stage.setInternshipProject(subject);
      stage.set_version(version);
      StageDTO updatedStage = stageDAO.modifyStage(stage);
      dalServices.commitTransaction();
      return updatedStage;
    } catch (Exception e) {
      LoggerUtil.logError("BizError", e);
      dalServices.rollbackTransaction();
      throw e;
    }
  }


}
