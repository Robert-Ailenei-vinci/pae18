package be.vinci.pae.business.domain;

/**
 * This class represents an implementation of the {@link Entreprise} interface.
 */
public class EntrepriseImpl implements Entreprise {

  private int id;
  private String tradeName;
  private String designation;
  private String address;
  private String phoneNumber;
  private String email;
  private boolean isBlacklisted;
  private String blacklistReason;

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String getTradeName() {
    return tradeName;
  }

  @Override
  public void setTradeName(String tradeName) {
    this.tradeName = tradeName;
  }

  @Override
  public String getDesignation() {
    return designation;
  }

  @Override
  public void setDesignation(String designation) {
    this.designation = designation;
  }

  @Override
  public String getAddress() {
    return address;
  }

  @Override
  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public String getPhoneNumber() {
    return phoneNumber;
  }

  @Override
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean isBlacklisted() {
    return isBlacklisted;
  }

  @Override
  public void setIsBlacklisted(boolean isBlacklisted) {
    this.isBlacklisted = isBlacklisted;
  }

  /**
   * Sets the reason for blacklisting the enterprise.
   *
   * @param reason
   */
  @Override
  public void setBlacklistReason(String reason) {
    this.blacklistReason = reason;
  }

  /**
   * Retrieves the reason for blacklisting the enterprise.
   *
   * @return the reason for blacklisting the enterprise
   */
  @Override
  public String getBlacklistReason() {
    return blacklistReason;
  }
}
