package be.vinci.pae.services;

import be.vinci.pae.business.domain.StageDTO;

/**
 * The interface StageDAO represents the data access for managing {@link StageDTO} objects.
 */
public interface StageDAO {

  StageDTO getOneStageByUserId(int userId);
}
