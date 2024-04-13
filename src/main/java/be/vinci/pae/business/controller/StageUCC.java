package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.StageDTO;

/**
 * The interface StageUCC represents the controller for managing {@link StageDTO} objects.
 */
public interface StageUCC {

  /**
   * Retrieves the stage associated with the given user ID.
   *
   * @param userId the ID of the user
   * @return the stage associated with the user
   */
  StageDTO getOneStageByUserId(int userId);

  /**
   * Creates a new stage with the provided contact, signature date, internship project, and
   * supervisor ID.
   *
   * @param contact           the contact associated with the stage
   * @param signatureDate     the date of signature
   * @param internshipProject the project of the internship
   * @param supervisorId      the ID of the supervisor
   * @return the created stage
   */
  StageDTO createOne(ContactDTO contact, String signatureDate, String internshipProject,
      int supervisorId);

  /**
   * Modifies the stage with the provided user ID, subject, contact ID, and version.
   *
   * @param userId    the ID of the user
   * @param subject   the subject of the stage
   * @param contactId the ID of the contact
   * @param version   the version of the stage
   * @return the modified stage
   */
  StageDTO modifyStage(int userId, String subject, int contactId, int version);
}
