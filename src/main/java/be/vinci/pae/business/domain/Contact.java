package be.vinci.pae.business.domain;

import java.util.List;

/**
 * This interface extends {@link ContactDTO} and represents a contact.
 */
public interface Contact extends ContactDTO {

  /**
   * Checks if the contact has been marked as stopped to follow.
   *
   * @return {@code true} if the contact is marked as stopped to follow, {@code false} otherwise.
   */
  boolean checkStopFollow();

  /**
   * Checks if the contact has been marked as refused.
   *
   * @return {@code true} if the contact is marked as refused, {@code false} otherwise.
   */
  boolean checkRefused();

  /**
   * Checks if the contact has been marked as met.
   *
   * @return {@code true} if the contact is marked as met, {@code false} otherwise.
   */
  boolean checkMeet();

  /**
   * Checks the uniqueness of a contact based on the combination of user, enterprise, and school
   * year.
   *
   * @param userContacts  The list of contacts associated with the user.
   * @param enterpriseDTO The enterprise associated with the contact.
   * @param schoolYearDTO The school year associated with the contact.
   * @return {@code true} if the contact is unique based on the provided user, enterprise, and
   *     school year, {@code false} otherwise.
   */
  boolean checkUniqueUserEnterpriseSchoolYear(List<ContactDTO> userContacts,
      EntrepriseDTO enterpriseDTO, SchoolYearDTO schoolYearDTO);
}