package be.vinci.pae.services;

import be.vinci.pae.business.domain.StageDTO;

/**
 * The interface StageDAO represents the data access for managing {@link StageDTO} objects.
 */
public interface StageDAO {

  /**
   * Retrieves a stage by its student id.
   *
   * @param userId The id of the student linked to the stage to retrieve.
   * @return The {@link StageDTO} representing the stage.
   */
  StageDTO getOneStageByUserId(int userId);

  /**
   * Modifies a stage.
   *
   * @param stageDTO The {@link StageDTO} representing the stage to modify.
   * @return The modified {@link StageDTO} representing the stage.
   */
  StageDTO modifyStage(StageDTO stageDTO);
}
