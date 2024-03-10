package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.StageDTO;

public interface StageUCC {



  /**
   * Get the stage of the user.
   *
   * @param userId the user id
   * @return the stage of the user
   */
  StageDTO getOneStageByUserId(int userId);
}
