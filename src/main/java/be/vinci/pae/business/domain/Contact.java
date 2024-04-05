package be.vinci.pae.business.domain;

/**
 * This interface extends {@link ContactDTO} and represents a contact entity.
 */
public interface Contact extends ContactDTO {


  /**
   * Checks if the contact has been marked as stopped to follow.
   *
   * @param version The version of the contact in the frontend.
   * @return {@code true} if the contact is marked as stopped to follow, {@code false} otherwise.
   */
  boolean stopFollowContact(int version);

  /**
   * Checks if the contact can be refused and changes its state accordingly.
   *
   * @param refusalReason The refusal reason.
   * @param version       The version of the contact in the frontend.
   * @return {@code true} if it can be refused, {@code false} otherwise.
   */
  boolean refuseContact(String refusalReason, int version);


  /**
   * Checks if the contact can be met and sets its state accordingly.
   *
   * @param meetingType The type of meeting.
   * @param version     The version of the contact in the frontend.
   * @return {@code true} if it can be met, {@code false} otherwise.
   */
  boolean meetContact(String meetingType, int version);

  /**
   * Checks the uniqueness of a contact based on the combination of user, enterprise, and school
   * year.
   *
   * @param wantedEntrepriseId The ID of the desired enterprise.
   * @param wantedSchoolYearId The ID of the desired school year.
   * @return {@code true} if the contact is unique based on the provided user, enterprise, and
   * school year, {@code false} otherwise.
   */
  boolean checkUniqueUserEnterpriseSchoolYear(
      int wantedEntrepriseId,
      int wantedSchoolYearId);

  /**
   * Checks if the contact can be accepted and changes its state accordingly.
   *
   * @param version The version of the contact in the frontend.
   * @return {@code true} if it can be accepted, {@code false} otherwise.
   */
  boolean acceptContact(int version);

  /**
   * Checks if the contact's state is accepted.
   *
   * @return {@code true} if the contact's state is accepted, {@code false} otherwise.
   */
  boolean checkStateAccepted();

  /**
   * Cancels the contact.
   *
   * @param version The version of the contact in the frontend.
   * @return {@code true} if the contact is successfully canceled, {@code false} otherwise.
   */
  boolean cancelContact(int version);
}
