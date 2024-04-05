package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.StageDTO;

/**
 * The interface StageUCC represents the controller for managing {@link StageDTO} objects.
 */
public interface StageUCC {

  /**
   * Get the stage of the user.
   *
   * @param userId the user id
   * @return the stage of the user
   */
  StageDTO getOneStageByUserId(int userId);

  StageDTO createOne(ContactDTO contact, String signatureDate, String internshipProject,
      int supervisorId);
}
