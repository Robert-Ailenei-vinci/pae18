package be.vinci.pae.business.domain;

/**
 * This interface represents a data transfer object for an enterprise.
 */
public interface EntrepriseDTO {

  //TODO: change fields to match database name

  /**
   * Retrieves the identifier of the enterprise.
   *
   * @return the identifier of the enterprise
   */
  int getId();

  /**
   * Sets the identifier of the enterprise.
   *
   * @param id the identifier to set for the enterprise
   */
  void setId(int id);

  /**
   * Retrieves the trade name of the enterprise.
   *
   * @return the trade name of the enterprise
   */
  String getTradeName();

  /**
   * Sets the trade name of the enterprise.
   *
   * @param tradeName the trade name to set for the enterprise
   */
  void setTradeName(String tradeName);

  /**
   * Retrieves the designation of the enterprise.
   *
   * @return the designation of the enterprise
   */
  String getDesignation();

  /**
   * Sets the designation of the enterprise.
   *
   * @param designation the designation to set for the enterprise
   */
  void setDesignation(String designation);

  /**
   * Retrieves the address of the enterprise.
   *
   * @return the address of the enterprise
   */
  String getAddress();

  /**
   * Sets the address of the enterprise.
   *
   * @param address the address to set for the enterprise
   */
  void setAddress(String address);

  /**
   * Retrieves the phone number of the enterprise.
   *
   * @return the phone number of the enterprise
   */
  String getPhoneNumber();

  /**
   * Sets the phone number of the enterprise.
   *
   * @param phoneNumber the phone number to set for the enterprise
   */
  void setPhoneNumber(String phoneNumber);

  /**
   * Retrieves the email of the enterprise.
   *
   * @return the email of the enterprise
   */
  String getEmail();

  /**
   * Sets the email of the enterprise.
   *
   * @param email the email to set for the enterprise
   */
  void setEmail(String email);

  /**
   * Checks if the enterprise is blacklisted.
   *
   * @return true if the enterprise is blacklisted, false otherwise
   */
  boolean isBlacklisted();

  /**
   * Sets whether the enterprise is blacklisted.
   *
   * @param isBlacklisted true if the enterprise is blacklisted, false otherwise
   */
  void setIsBlacklisted(boolean isBlacklisted);

  /**
   * Retrieves the reason for blacklisting the enterprise.
   *
   * @return the reason for blacklisting the enterprise
   */
  String getBlacklistReason();

  /**
   * Sets the reason for blacklisting the enterprise.
   *
   * @param blacklistReason the reason for blacklisting the enterprise
   */
  void setBlacklistReason(String blacklistReason);
}
