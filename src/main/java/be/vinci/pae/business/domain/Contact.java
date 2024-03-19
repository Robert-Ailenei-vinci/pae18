package be.vinci.pae.business.domain;

/**
 * This interface extends {@link ContactDTO} and represents a contact.
 */
public interface Contact extends ContactDTO {

  /**
   * checkStopFollow.
   *
   * @return true or false.
   */
  boolean checkStopFollow();

  /**
   * checkRefused.
   *
   * @return true or false.
   */
  boolean checkRefused();

  /**
   * checkMeet.
   *
   * @return true or false.
   */
  boolean checkMeet();
}
