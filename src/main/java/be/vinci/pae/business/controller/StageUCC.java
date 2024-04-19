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

  /**
   * Create a stage.
   *
   * @param contact         the contact
   * @param signatureDate   the signature date
   * @param internshipProject the internship project
   * @param supervisorId    the supervisor id
   * @return the created stage
   */
  StageDTO createOne(ContactDTO contact, String signatureDate, String internshipProject,
      int supervisorId);


  /**
   * Modify the stage.
   *
   * @param userId    the user id
   * @param subject   the subject
   * @param contactId the contact id
   * @param version   the version
   * @return the modified stage
   */
  StageDTO modifyStage(int userId, String subject, int contactId, int version);
}
