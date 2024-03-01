package be.vinci.pae.business.domain;

public interface EntrepriseDTO {
    int getId();
    void setId(int id);
    String getTradeName();
    void setTradeName(String tradeName);
    String getDesignation();
    void setDesignation(String designation);
    String getAddress();
    void setAddress(String address);
    String getPhoneNumber();
    void setPhoneNumber(String phoneNumber);
    String getEmail();
    void setEmail(String email);
    boolean isBlacklisted();
    void setIsBlacklisted(boolean isBlacklisted);
}
