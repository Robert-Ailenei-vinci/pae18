package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.StageDTO;
import be.vinci.pae.services.StageDAO;
import jakarta.inject.Inject;

public class StageUCCImpl implements StageUCC{
  @Inject
  private StageDAO stageDAO;

  @Override
  public StageDTO getOneStageByUserId(int userId) {
    return stageDAO.getOneStageByUserId(userId);
  }
}
