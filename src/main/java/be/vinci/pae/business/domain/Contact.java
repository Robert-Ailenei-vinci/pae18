package be.vinci.pae.business.domain;

/**
 * This interface extends {@link ContactDTO} and represents a contact.
 */
public interface Contact extends ContactDTO {

  boolean checkStopFollow();

  boolean checkRefused();

  boolean checkMeet();
}
