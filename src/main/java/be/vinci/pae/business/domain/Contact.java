package be.vinci.pae.business.domain;

import java.util.List;

/**
 * This interface extends {@link ContactDTO} and represents a contact.
 */
public interface Contact extends ContactDTO {


  /**
   * Checks if the contact has been marked as stopped to follow.
   *
   * @param version the version of the contact in the frontend.
   * @return {@code true} if the contact is marked as stopped to follow, {@code false} otherwise.
   */
  boolean stopFollowContact(int version);

  /**
   * Checks if the contact can be refused and change it.
   *
   * @param refusalReason the refusal reason
   * @param version       the version of the contact in the frontend.
   * @return true if it can be refused or false if not
   */
  boolean refuseContact(String refusalReason, int version);


  /**
   * Checks if the contact can be met and set it.
   *
   * @param meetingType the type of meeting.
   * @param version     the version of the contact in the frontend.
   * @return true if it can be met, fasle if not
   */
  boolean meetContact(String meetingType, int version);

  /**
   * Checks the uniqueness of a contact based on the combination of user, enterprise, and school
   * year.
   *
   * @param userContacts  The list of contacts associated with the user.
   * @param enterpriseDTO The enterprise associated with the contact.
   * @param schoolYearDTO The school year associated with the contact.
   * @return {@code true} if the contact is unique based on the provided user, enterprise, and
   * school year, {@code false} otherwise.
   */
  boolean checkUniqueUserEnterpriseSchoolYear(List<ContactDTO> userContacts,
      EntrepriseDTO enterpriseDTO, SchoolYearDTO schoolYearDTO);
}