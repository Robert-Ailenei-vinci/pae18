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
   * @return The {@link StageDTO} representing the stage, or {@code null} if no stage is found.
   */
  StageDTO getOneStageByUserId(int userId);

  /**
   * Creates a new stage.
   *
   * @param contactId         The id of the contact associated with the stage.
   * @param signatureDate     The signature date of the stage contract.
   * @param internshipProject The description of the internship project.
   * @param supervisorId      The id of the supervisor associated with the stage.
   * @param userId            The id of the student associated with the stage.
   * @param schoolYearId      The id of the school year associated with the stage.
   * @return The {@link StageDTO} representing the newly created stage.
   */
  StageDTO createOne(int contactId, String signatureDate, String internshipProject,
      int supervisorId, int userId, int schoolYearId);
}
