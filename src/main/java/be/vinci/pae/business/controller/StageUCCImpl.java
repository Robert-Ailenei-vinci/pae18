package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.StageDTO;
import be.vinci.pae.services.StageDAO;
import jakarta.inject.Inject;

/**
 * This class is an implementation of the {@link StageUCC} interface.
 */
public class StageUCCImpl implements StageUCC {
  @Inject
  private StageDAO stageDAO;

  @Override
  public StageDTO getOneStageByUserId(int userId) {
    return stageDAO.getOneStageByUserId(userId);
  }
}
